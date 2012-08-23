package siver.cox;

import java.util.HashMap;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.river.River.NoLaneFound;
import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class CoxObservations {
	private Cox cox;
	private Boat boat;
	private BoatNavigation navigator;
	
	private static final int CLEAR_BOUNDARY = 3;
	private static final double OVERTAKING_SPEED_DIFFERENCE = 1.0;
	
	private HashMap<String, Boolean> frozen_observations;
	
	private CoxVision vision;
	private static final String outingComplete="outingComplete", 
			atRiversEnd="atRiversEnd", 
			belowDesiredSpeed="belowDesiredSpeed", 
			aboveDesiredSpeed="aboveDesiredSpeed", 
			slowBoatInfront="slowBoatInfront", 
			changingLane="changingLane", 
			laneToRightIsClear="laneToRightIsClear", 
			laneToLeftIsClear="laneToLeftIsClear",
			nearbyBoatInfront = "nearbyBoatInfront";
	
	public CoxObservations(Cox cox, Boat boat, BoatNavigation navigator) {
		this.cox = cox;
		this.boat = boat;
		this.navigator = navigator;
		frozen_observations = new HashMap<String, Boolean>();
	}
	
	public static CoxObservations make(Cox cox, Boat boat, BoatNavigation navigator) {
		CoxObservations obs = new CoxObservations(cox, boat, navigator);
		
		obs.freeze();
		
		return obs;
	}
	
	public Integer boatGear() {
		return boat.getGear();
	}
	
	public Boolean outingComplete() {
		return retrieveFrozenObservation(outingComplete);
	}
	
	public boolean atRiversEnd() {
		return retrieveFrozenObservation(atRiversEnd);
	}
	
	public boolean belowDesiredSpeed() {
		return retrieveFrozenObservation(belowDesiredSpeed);
	}
	
	public boolean aboveDesiredSpeed() {
		return retrieveFrozenObservation(aboveDesiredSpeed);
	}
	
	public boolean changingLane() {
		return retrieveFrozenObservation(changingLane);
	}
	
	public boolean nearbyBoatInfront() {
		return retrieveFrozenObservation(nearbyBoatInfront);
	}
	
	public boolean slowBoatInfront() {
		return retrieveFrozenObservation(slowBoatInfront);
	}
	
	public boolean laneToLeftIsClear() {
		return retrieveFrozenObservation(laneToLeftIsClear);
	}
	
	public boolean laneToRightIsClear() {
		return retrieveFrozenObservation(laneToRightIsClear);
	}
	
	private void freeze() {
		freezeVision(CoxVision.look(cox, boat, navigator));
		freezeOutingOver();
		freezeAtRiversEnd();
		freezeBelowDesiredSpeed();
		freezeAboveDesiredSpeed();
		freezeChangingLane();
		freezeNearbyBoatInfront();
		freezeSlowBoatInfront();
		freezeLaneToLeftIsClear();
		freezeLaneToRightIsClear();
	}
	
	protected void freezeVision(CoxVision sight) {
		this.vision = sight;
	}
	
	protected void freezeOutingOver() {
		Boolean value = (boat.total_distance_covered() >= cox.getGoalDistance()) && (navigator.getDestinationNode().equals(navigator.getLane().getStartNode()));
		frozen_observations.put(outingComplete, value);
	}
	
	protected void freezeAtRiversEnd() { 
		LaneNode node = navigator.getDestinationNode();
		LaneEdge next_edge = node.getLane().getNextEdge(node, navigator.headingUpstream());
		Boolean value = (next_edge == null);
		frozen_observations.put(atRiversEnd, value);
	}
	
	protected void freezeBelowDesiredSpeed() {
		Boolean value = boat.getGear() < cox.desired_gear();
		frozen_observations.put(belowDesiredSpeed, value);
	}
	
	protected void freezeAboveDesiredSpeed() {
		Boolean value = boat.getGear() > cox.desired_gear();
		frozen_observations.put(aboveDesiredSpeed, value);
	}
	
	protected void freezeChangingLane() {
		Boolean value = navigator.changingLane();;
		frozen_observations.put(changingLane, value);
	}
	
	protected void freezeNearbyBoatInfront() {
		//check that cannot see as far as CLEAR_BOUNDARY and that the limiting factor is a blocked edge (not the end of the river)
		Boolean value = (vision.edgesOfClearRiver(navigator.getLane(), true) < CLEAR_BOUNDARY) && 
				(vision.blockedEdge(navigator.getLane(), true) != null);
		frozen_observations.put(nearbyBoatInfront, value);
	}
	
	protected void freezeSlowBoatInfront() {
		frozen_observations.put(slowBoatInfront, calculateSlowBoatInfront());
	}
	
	protected void freezeLaneToLeftIsClear() {
		frozen_observations.put(laneToLeftIsClear, calculateLaneToLeftIsClear());
	}
	
	protected void freezeLaneToRightIsClear() {
		frozen_observations.put(laneToRightIsClear, calculateLaneToRightIsClear());
	}
	
	private boolean calculateSlowBoatInfront() {
		LaneEdge occupiedEdgeAhead = (LaneEdge) vision.blockedEdge(navigator.getLane(),true);
		if(occupiedEdgeAhead == null) {
			// return false if there is no occupied edge ahead
			return false;
		}
		
		for(Cox coxInfront : occupiedEdgeAhead.getCoxes()) {			
			if(cox.getNavigator().headingUpstream() == coxInfront.getNavigator().headingUpstream()) {
				//if boats are travelling in the same direction then want the difference in their speeds to be greater than limit
				return (boat.getSpeed() - coxInfront.getBoat().getSpeed()) > OVERTAKING_SPEED_DIFFERENCE;
			} else {
				//if boats are travelling in the different direction then want the sum of their speeds to be greater than limit
				return (boat.getSpeed() + coxInfront.getBoat().getSpeed()) > OVERTAKING_SPEED_DIFFERENCE;
			}
			
		}
		return false;
	}
	
	private boolean calculateLaneToLeftIsClear() {
		try {
			Lane laneToLeft = boat.getRiver().getLaneToLeftOf(navigator.getLane(), navigator.headingUpstream());
			return laneIsClear(laneToLeft);
		} catch (NoLaneFound e) {
			//if there is no lane to the left, then it is not clear
			return false;
		}
	}
	
	private boolean calculateLaneToRightIsClear() {
		try {
			Lane laneToRight = boat.getRiver().getLaneToRightOf(navigator.getLane(), navigator.headingUpstream());
			return laneIsClear(laneToRight);
		} catch (NoLaneFound e) {
			//if there is no lane to the left, then it is not clear
			return false;
		}
	}
	
	private boolean laneIsClear(Lane lane) {
		//return true only if there's nothing ahead and nothing behind
		//very simplistic at the moment, only checks existence, not relative speed
		return (vision.edgesOfClearRiver(lane, true) >= CLEAR_BOUNDARY) && 
				(vision.edgesOfClearRiver(lane, false) >= CLEAR_BOUNDARY);
	}
	
	private Boolean retrieveFrozenObservation(String key) {
		Boolean output = frozen_observations.get(key);
		if(output == null) throw new NullPointerException(key + " has not been set");
		return output;
	}
	
}
