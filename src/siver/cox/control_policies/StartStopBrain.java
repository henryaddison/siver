package siver.cox.control_policies;

import java.lang.reflect.InvocationTargetException;

import siver.cox.actions.*;

public class StartStopBrain extends CoxBrain {
	private boolean speedUp = true;
	
	@Override
	public Class<? extends Action> typeSpecificActionChoice() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		if(latestObservations.atRiversEnd()) {
			return Spin.class;
		}
		if(latestObservations.boatGear() == 0) {
			speedUp = true;
			return SpeedUp.class;
		}
		if(latestObservations.boatGear() == 10) {
			speedUp = false;
			return SlowDown.class;
		}
		else if(speedUp) {
			return SpeedUp.class;
		}
		else if(true) {
			return SlowDown.class;
		}
		throw new RuntimeException("No action chosen by brain. Something has gone very wrong");
	}
}
