package siver.cox.observations;

import java.util.ArrayList;
import java.util.Collections;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.cox.Cox;
import siver.cox.CoxObservations;
import siver.river.River.NoLaneFound;
import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;

public abstract class LaneObservation extends AbstractObservation {

	public class Blockage {
		private Double max_relative_speed, min_relative_speed;
		Integer edges_away;

		public Integer getEdgesAway() {
			return edges_away;
		}

		public void setEdgesAway(Integer edges_away) {
			this.edges_away = edges_away;
		}

		public Double getMaxRelativeSpeed() {
			return max_relative_speed;
		}

		public void setMaxRelativeSpeed(Double max_relative_speed) {
			this.max_relative_speed = max_relative_speed;
		}

		public Double getMinRelativeSpeed() {
			return min_relative_speed;
		}

		public void setMinRelativeSpeed(Double min_relative_speed) {
			this.min_relative_speed = min_relative_speed;
		}
	}

	public LaneObservation(CoxObservations obs, Cox cox, Boat boat,
			BoatNavigation nav) {
		super(obs, cox, boat, nav);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Blockage getValue() {
		if(value == null) {
			calculateValue();
		}
		return (Blockage) value;
	}
	
	protected abstract Lane getLane() throws NoLaneFound;
	protected abstract boolean getInfront();
	
	@Override
	protected void calculateValue() {
		value = new Blockage();
		Blockage cast_value = (Blockage) value;
		try {
			cast_value.setEdgesAway((Integer) getVision().edgesOfClearRiver(getLane(),getInfront()));
		} catch (NoLaneFound e) {
			//if there is no lane, then set value to 0
			cast_value.setEdgesAway(0);
		}
		
		ArrayList<Double> speeds = collectSpeeds();
		if(speeds.isEmpty()) {
			cast_value.setMaxRelativeSpeed(0.0);
			cast_value.setMinRelativeSpeed(0.0);
		} else {
			cast_value.setMaxRelativeSpeed(Collections.max(speeds));
			cast_value.setMinRelativeSpeed(Collections.min(speeds));
		}
	}
	
	private ArrayList<Double> collectSpeeds() {
		ArrayList<Double> speeds = new ArrayList<Double>();
		
		LaneEdge occupiedEdgeAhead;
		try {
			occupiedEdgeAhead = (LaneEdge) getVision().blockedEdge(getLane(),getInfront());
		} catch (NoLaneFound e) {
			// return empty list if no lane found
			return speeds;
		}
		if(occupiedEdgeAhead == null) {
			// return empty list if there is no occupied edge ahead
			return speeds;
		}
		
		for(Cox coxInfront : occupiedEdgeAhead.getCoxes()) {
			if(cox.getNavigator().headingUpstream() == coxInfront.getNavigator().headingUpstream()) {
				//if boats are travelling in the same direction then want the difference in their speeds
				speeds.add(boat.getSpeed() - coxInfront.getBoat().getSpeed());
				
			} else {
				//if boats are travelling in the different direction then want the sum of their speeds
				speeds.add(boat.getSpeed() + coxInfront.getBoat().getSpeed());
			}
		}
		return speeds;
	}
	
	protected Lane getLeftLane() throws NoLaneFound {
		return boat.getRiver().getLaneToLeftOf(navigator.getLane(), navigator.headingUpstream());
	}
	
	protected Lane getRightLane() throws NoLaneFound {
		return boat.getRiver().getLaneToRightOf(navigator.getLane(), navigator.headingUpstream());
	}
	
	protected Lane getCurrentLane() {
		return navigator.getLane();
	}

}
