package siver.cox.brains;

import siver.cox.actions.*;

public class ConservativeBrain extends CoxBrain {
	@Override
	public Class<? extends Action> typeSpecificActionChoice() {
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