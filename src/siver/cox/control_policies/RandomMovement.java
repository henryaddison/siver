package siver.cox.control_policies;

import java.lang.reflect.InvocationTargetException;

import siver.cox.actions.*;

public class RandomMovement extends RandomChoice {
	private static final Class[] possible_actions = {LetBoatRun.class, MoveToLaneOnLeft.class, MoveToLaneOnRight.class, SpeedUp.class, SlowDown.class};
	
	@Override
	public Class<? extends Action> typeSpecificActionChoice() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		if(latestObservations.atRiversEnd()) {
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
