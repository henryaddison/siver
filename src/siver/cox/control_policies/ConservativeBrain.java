package siver.cox.control_policies;

import java.lang.reflect.InvocationTargetException;

import siver.cox.actions.*;

public class ConservativeBrain extends CoxBrain {
	@Override
	public Class<? extends Action> typeSpecificActionChoice() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		if(latestObservations.atRiversEnd()) {
			return Spin.class;
		}
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
