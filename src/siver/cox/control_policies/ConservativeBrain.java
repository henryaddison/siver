package siver.cox.control_policies;

import java.lang.reflect.InvocationTargetException;

import siver.cox.actions.*;
import siver.cox.observations.LaneObservation.Blockage;

public class ConservativeBrain extends CoxBrain {
	private static final int CLEAR_BOUNDARY = 3;
	
	@Override
	public Class<? extends Action> typeSpecificActionChoice() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		if(latestObservations.atRiversEnd()) {
			return Spin.class;
		}
		if(nearbyBoatInfront()) {
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
	
	private boolean nearbyBoatInfront() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Blockage blockage = latestObservations.aheadCurrentLaneLook();
		return (blockage.getEdgesAway() < CLEAR_BOUNDARY) && (blockage.getBlockedEdge() != null);
	}

}
