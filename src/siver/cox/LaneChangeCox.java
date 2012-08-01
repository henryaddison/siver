package siver.cox;

import siver.cox.actions.*;

public class LaneChangeCox extends CoxBrain {
	public LaneChangeCox(CoxObservations obs) {
		super(obs);
	}

	private boolean move_to_left = true;
	private int countDown = 10;
	
	@Override
	public Class chooseAction() {
		if(observations.atRiversEnd()) {
			return Spin.class;
		}
		if(observations.belowDesiredSpeed()) {
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
