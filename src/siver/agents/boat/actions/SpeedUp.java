package siver.agents.boat.actions;

import siver.agents.boat.CoxAgent;

public class SpeedUp extends SingleTickAction {
	
	public SpeedUp(CoxAgent cox) {
		super(cox);
	}
	
	@Override
	public void doExecute() {
		boat.shiftUp();
	}

}
