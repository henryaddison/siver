package siver.cox.brains;

import siver.cox.CoxObservations;
import siver.cox.actions.*;

public class RandomMovement extends RandomChoice {
	
	private static final Class[] possible_actions = {LetBoatRun.class, MoveToLaneOnLeft.class, MoveToLaneOnRight.class, SpeedUp.class, SlowDown.class};
	
	public RandomMovement(CoxObservations obs) {
		super(obs);
	}
	
	@Override
	public Class<? extends Action> chooseAction() {
		if(observations.atRiversEnd()) {
			return Spin.class;
		}
		if(true) {
			return chooseRandomAction();
		}
		throw new RuntimeException("No action chosen by brain. Something has gone very wrong");
	}
	
	@Override
	protected Class[] possible_actions() {
		return possible_actions;
	}
}
