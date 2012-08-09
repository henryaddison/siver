package siver.cox.brains;

import repast.simphony.random.RandomHelper;
import siver.cox.CoxObservations;
import siver.cox.actions.*;

public class RandomChoice extends CoxBrain {
	
	public RandomChoice(CoxObservations obs) {
		super(obs);
	}
	
	private static final Class[] possible_actions = {LetBoatRun.class, MoveToLaneOnLeft.class, MoveToLaneOnRight.class, SpeedUp.class, SlowDown.class, Spin.class};
	
	@Override
	public Class chooseAction() {
		if(true) {
			return chooseRandomAction();
		}
		return null;
	}
	
	protected Class[] possible_actions() {
		return possible_actions;
	}
	
	protected Class chooseRandomAction() {
		int index = RandomHelper.nextIntFromTo(0, possible_actions().length-1);
		return possible_actions()[index];
	}

}
