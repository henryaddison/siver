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
		int index = RandomHelper.nextIntFromTo(0, possible_actions.length-1);
		return possible_actions[index];
	}

}
