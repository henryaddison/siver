package siver.agents.boat.actions;

import siver.agents.boat.CoxAgent;

public class SlowDown extends Action {	
	public SlowDown(CoxAgent cox) {
		super(cox);
	}
	@Override
	public void execute() {
		boat.shiftDown();
	}

}
