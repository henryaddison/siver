package siver.cox.brains;

import siver.cox.CoxObservations;
import siver.cox.actions.*;

public class StartStopBrain extends CoxBrain {
	public StartStopBrain(CoxObservations obs) {
		super(obs);
	}

	private boolean speedUp = true;
	
	@Override
	public Class chooseAction() {
		if(observations.atRiversEnd()) {
			return Spin.class;
		}
		if(observations.boatGear() == 0) {
			speedUp = true;
			return SpeedUp.class;
		}
		if(observations.boatGear() == 10) {
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