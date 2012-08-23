package siver.cox.control_policies;

import java.lang.reflect.InvocationTargetException;

import siver.cox.actions.*;

public class LaneChangeBrain extends CoxBrain {
	private boolean move_to_left = true;
	private int countDown = 10;
	
	@Override
	public Class<? extends Action> typeSpecificActionChoice() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		if(latestObservations.atRiversEnd()) {
			return Spin.class;
		}
		if(latestObservations.belowDesiredSpeed()) {
			return SpeedUp.class;
		}
		if(move_to_left & countDown == 0) {
			move_to_left = !move_to_left;
			countDown = 50;
			return  MoveToLaneOnLeft.class;
		}
		if(!move_to_left & countDown == 0) {
			move_to_left = !move_to_left;
			countDown = 50;
			return  MoveToLaneOnRight.class;
		}
		if(true) {
			countDown--;
			return LetBoatRun.class;
		}
		
		throw new RuntimeException("No action chosen by brain. Something has gone very wrong");
	}
}
