package siver.agents.boat;

import siver.agents.boat.actions.*;

public class LaneChangeCox extends CoxAgent {
	private boolean move_to_left = true;
	private int countDown = 10;
	@Override
	public void chooseAction() {
		if(backAtBoatHouse()) {
			action = new Land(this);
		}
		else if(atRiversEnd()) {
			action = new Spin(this);
		}
		else if(belowDesiredSpeed()) {
			action = new SpeedUp(this);
		}
		else if(move_to_left & countDown == 0) {
			action = new MoveToLaneOnLeft(this);
			move_to_left = !move_to_left;
			countDown = 50;
		}
		else if(!move_to_left & countDown == 0) {
			action = new MoveToLaneOnRight(this);
			move_to_left = !move_to_left;
			countDown = 50;
		}
		else if(true) {
			action = new LetBoatRun(this);
			countDown--;
		}
	}
}
