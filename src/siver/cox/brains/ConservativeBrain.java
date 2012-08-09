package siver.cox.brains;

import siver.cox.CoxObservations;
import siver.cox.actions.*;

public class ConservativeBrain extends CoxBrain {

	public ConservativeBrain(CoxObservations obs) {
		super(obs);
	}

	@Override
	public Class chooseAction() {
		if(observations.atRiversEnd()) {
			return Spin.class;
		}
		if(observations.nearbyBoatInfront()) {
			return SlowDown.class;
		}
		if(observations.belowDesiredSpeed()) {
			return SpeedUp.class;
		}
		if(true) {
			return LetBoatRun.class;
		}
		return null;
	}

}
