package siver.cox.control_policies;

import java.lang.reflect.InvocationTargetException;

import siver.cox.actions.*;

public class OvertakingBrain extends CoxBrain {
	private boolean overtaking = false;
	
	
	@Override
	protected Class<? extends Action> typeSpecificActionChoice() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
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
	
	private Class<? extends Action> overtakingActionChoice() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		if(latestObservations.laneToRightIsClear() && !latestObservations.changingLane()) {
			overtaking = false;
			return MoveToLaneOnRight.class;
		}
		if(true) {
			return continueInLaneChoice();
		}
		throw new RuntimeException("No action chosen by brain. Something has gone very wrong");
	}
	
	private Class<? extends Action> continueInLaneChoice() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		if(latestObservations.belowDesiredSpeed()) {
			return SpeedUp.class;
		}
		if(true) {
			return LetBoatRun.class;
		}
		throw new RuntimeException("No action chosen by brain. Something has gone very wrong");
	}

}
