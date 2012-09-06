package siver.cox.control_policies;

import java.lang.reflect.InvocationTargetException;

import siver.cox.actions.Action;
import siver.cox.actions.MoveToLaneOnLeft;
import siver.cox.actions.SlowDown;
import siver.cox.actions.Spin;
import siver.cox.observations.LaneObservation.Blockage;

public class SaferOvertaking extends Overtaking {
	private static final double OVERTAKING_SPEED_DIFFERENCE = 1.0;
	private static final int AHEAD_CLEAR_BOUNDARY = 4;
	private static final int BEHIND_CLEAR_BOUNDARY = 2;
	
	
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
		if(nearbyBoatInfront()) {
			return SlowDown.class;
		}
		if(true) {
			return continueInLaneChoice();
		}
		throw new RuntimeException("No action chosen by brain. Something has gone very wrong");
	}
	
	private boolean nearbyBoatInfront() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Blockage blockage = latestObservations.aheadCurrentLaneLook();
		return (blockage.getEdgesAway() < AHEAD_CLEAR_BOUNDARY) && (blockage.getBlockedEdge() != null);
	}
	
	protected boolean slowBoatInfront() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Blockage blockage = latestObservations.aheadCurrentLaneLook();
		return nearbyBoatInfront() && blockage.getMaxRelativeSpeed() > OVERTAKING_SPEED_DIFFERENCE;
	}
	
	protected boolean laneToLeftIsClear() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Blockage aheadLeftblockage = latestObservations.aheadLeftLaneLook();
		Blockage behindLeftblockage = latestObservations.behindLeftLaneLook();
		return (aheadLeftblockage.getEdgesAway() >= AHEAD_CLEAR_BOUNDARY) && (behindLeftblockage.getEdgesAway() >= BEHIND_CLEAR_BOUNDARY); 
				
	}
	protected boolean laneToRightIsClear() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Blockage aheadRightblockage = latestObservations.aheadRightLaneLook();
		Blockage behindRightblockage = latestObservations.behindRightLaneLook();
		return (aheadRightblockage.getEdgesAway() >= AHEAD_CLEAR_BOUNDARY) && (behindRightblockage.getEdgesAway() >= BEHIND_CLEAR_BOUNDARY);
	}
}
