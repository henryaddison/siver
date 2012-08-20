package siver.cox.brains;

import siver.cox.actions.*;

public class OvertakingBrain extends CoxBrain {
	private boolean overtaking = false;
	
	
	@Override
	protected Class<? extends Action> typeSpecificActionChoice() {
		if(latestObservations.atRiversEnd()) {
			overtaking = false;
			return Spin.class;
		}
		if(overtaking) {
			return overtakingActionChoice();
		}
		if(latestObservations.aboveDesiredSpeed()) {
			return SlowDown.class;
		}
		if(latestObservations.slowBoatInfront() && latestObservations.laneToLeftIsClear()) {
			overtaking = true;
			return MoveToLaneOnLeft.class;
		}
		if(true) {
			return continueInLaneChoice();
		}
		throw new RuntimeException("No action chosen by brain. Something has gone very wrong");
	}
	
	private Class<? extends Action> overtakingActionChoice() {
		if(latestObservations.laneToRightIsClear() && !latestObservations.changingLane()) {
			overtaking = false;
			return MoveToLaneOnRight.class;
		}
		if(true) {
			return continueInLaneChoice();
		}
		throw new RuntimeException("No action chosen by brain. Something has gone very wrong");
	}
	
	private Class<? extends Action> continueInLaneChoice() {
		if(latestObservations.nearbyBoatInfront()) {
			return SlowDown.class;
		}
		if(latestObservations.belowDesiredSpeed()) {
			return SpeedUp.class;
		}
		if(true) {
			return LetBoatRun.class;
		}
		throw new RuntimeException("No action chosen by brain. Something has gone very wrong");
	}

}
