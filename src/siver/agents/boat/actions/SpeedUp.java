package siver.agents.boat.actions;

import siver.agents.boat.CoxAgent;

public class SpeedUp extends Action {
	
	public SpeedUp(CoxAgent cox) {
		super(cox);
	}
	
	@Override
	public void execute() {
		boat.shiftUp();
	}

}
