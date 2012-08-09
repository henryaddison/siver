package siver.cox.brains;

import siver.cox.CoxObservations;
import siver.cox.actions.*;

public class RandomMovement extends RandomChoice {
	
	private static final Class[] possible_actions = {LetBoatRun.class, MoveToLaneOnLeft.class, MoveToLaneOnRight.class, SpeedUp.class, SlowDown.class};
	
	public RandomMovement(CoxObservations obs) {
		super(obs);
	}
	
	@Override
	public Class chooseAction() {
		if(observations.atRiversEnd()) {
			return Spin.class;
		}
		if(true) {
			return chooseRandomAction();
		}
		return null;
	}
	
	@Override
	protected Class[] possible_actions() {
		return possible_actions;
	}
}
