package siver.cox.control_policies;

import java.lang.reflect.InvocationTargetException;

import siver.cox.actions.Action;
import siver.cox.actions.LetBoatRun;
import siver.cox.actions.MoveToLaneOnLeft;
import siver.cox.actions.MoveToLaneOnRight;
import siver.cox.actions.SlowDown;
import siver.cox.actions.SpeedUp;
import siver.cox.actions.Spin;
import siver.cox.observations.LaneObservation.Blockage;

public class DemoOvertaking extends ControlPolicy {
	private static final double OVERTAKING_SPEED_DIFFERENCE = 0.5;
	private static final int AHEAD_CLEAR_BOUNDARY = 3;
	private static final int BEHIND_CLEAR_BOUNDARY = 2;
	private static final int MOVING_BACK_AHEAD_CLEAR_BOUNDARY = 5;
	private static final int MOVING_BACK_BEHIND_CLEAR_BOUNDARY = 2;
	
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
		if(slowBoatInfront() && laneToLeftIsClear()) {
			overtaking = true;
			return MoveToLaneOnLeft.class;
		}
		if(true) {
			return continueInLaneChoice();
		}
		throw new RuntimeException("No action chosen by brain. Something has gone very wrong");
	}
	
	private Class<? extends Action> overtakingActionChoice() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		if(laneToRightIsClear() && !latestObservations.changingLane()) {
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
	
	protected boolean slowBoatInfront() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Blockage blockage = latestObservations.aheadCurrentLaneLook();
		return blockage.getEdgesAway() <= AHEAD_CLEAR_BOUNDARY && blockage.getMaxRelativeSpeed() > OVERTAKING_SPEED_DIFFERENCE;
	}
	
	protected boolean laneToLeftIsClear() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Blockage aheadLeftblockage = latestObservations.aheadLeftLaneLook();
		Blockage behindLeftblockage = latestObservations.behindLeftLaneLook();
		return (aheadLeftblockage.getEdgesAway() >= AHEAD_CLEAR_BOUNDARY) && (behindLeftblockage.getEdgesAway() >= BEHIND_CLEAR_BOUNDARY); 
				
	}
	
	protected boolean laneToRightIsClear() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Blockage aheadRightblockage = latestObservations.aheadRightLaneLook();
		Blockage behindRightblockage = latestObservations.behindRightLaneLook();
		return (aheadRightblockage.getEdgesAway() >= MOVING_BACK_AHEAD_CLEAR_BOUNDARY) && (behindRightblockage.getEdgesAway() >= MOVING_BACK_BEHIND_CLEAR_BOUNDARY);
	}

}
