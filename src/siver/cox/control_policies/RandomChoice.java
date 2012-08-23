package siver.cox.control_policies;

import java.lang.reflect.InvocationTargetException;

import repast.simphony.random.RandomHelper;
import siver.cox.actions.*;

public class RandomChoice extends CoxBrain {
	private static final Class[] possible_actions = {LetBoatRun.class, MoveToLaneOnLeft.class, MoveToLaneOnRight.class, SpeedUp.class, SlowDown.class, Spin.class};
	
	@Override
	public Class<? extends Action> typeSpecificActionChoice() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		if(true) {
			return chooseRandomAction();
		}
		throw new RuntimeException("No action chosen by brain. Something has gone very wrong");
	}
	
	protected Class<? extends Action>[] possible_actions() {
		return possible_actions;
	}
	
	protected Class<? extends Action> chooseRandomAction() {
		int index = RandomHelper.nextIntFromTo(0, possible_actions().length-1);
		return possible_actions()[index];
	}

}
