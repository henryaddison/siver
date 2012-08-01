package siver.boat;

import siver.agents.boat.actions.Action;
import siver.agents.boat.actions.LetBoatRun;
import siver.agents.boat.actions.SpeedUp;
import siver.agents.boat.actions.Spin;

public class BasicBrain extends CoxBrain {
	
	public BasicBrain(CoxObservations obs) {
		super(obs);
	}
	
	@Override
	public Class chooseAction() {
		if(observations.atRiversEnd()) {
			return Spin.class;
		}
		if(observations.belowDesiredSpeed()) {
			return SpeedUp.class;
		}
		if(true) {
			return LetBoatRun.class;
		}
		throw new RuntimeException("No action chosen by brain. Something has gone very wrong");
	}

}
