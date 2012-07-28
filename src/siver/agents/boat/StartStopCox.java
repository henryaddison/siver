package siver.agents.boat;

import siver.agents.boat.actions.*;

public class StartStopCox extends CoxAgent {
	private boolean speedUp = true;
	
	@Override
	public void chooseAction() {
		if(backAtBoatHouse()) {
			action = new Land(this);
		}
		else if(atRiversEnd()) {
			action = new Spin(this);
		}
		else if(boat.getGear() == 0) {
			speedUp = true;
			action = new SpeedUp(this);
		}
		else if(boat.getGear() == 10) {
			speedUp = false;
			action = new SlowDown(this);
		}
		else if(speedUp) {
			action = new SpeedUp(this);
		}
		else if(true) {
			action = new SlowDown(this);
		}
	}
}