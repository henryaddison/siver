package siver.cox.control_policies;

import siver.cox.actions.*;

public class BasicBrain extends CoxBrain {
	@Override
	protected Class<? extends Action> typeSpecificActionChoice() {
		if(latestObservations.atRiversEnd()) {
			return Spin.class;
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
