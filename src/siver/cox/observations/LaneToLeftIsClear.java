package siver.cox.observations;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.cox.Cox;
import siver.cox.CoxObservations;
import siver.river.River.NoLaneFound;
import siver.river.lane.Lane;

public class LaneToLeftIsClear extends BooleanObservation {
	public LaneToLeftIsClear(CoxObservations obs, Cox cox, Boat boat,
			BoatNavigation nav) {
		super(obs, cox, boat, nav);
		// TODO Auto-generated constructor stub
	}

	private static final int CLEAR_BOUNDARY = 3;
	@Override
	protected void calculateValue() {
		value = calculateLaneToLeftIsClear();

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
	
	private boolean laneIsClear(Lane lane) {
		//return true only if there's nothing ahead and nothing behind
		//very simplistic at the moment, only checks existence, not relative speed
		return (getVision().edgesOfClearRiver(lane, true) >= CLEAR_BOUNDARY) && 
				(getVision().edgesOfClearRiver(lane, false) >= CLEAR_BOUNDARY);
	}

}
