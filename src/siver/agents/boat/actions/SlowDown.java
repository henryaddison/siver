package siver.agents.boat.actions;

import siver.agents.boat.CoxAgent;

public class SlowDown extends SingleTickAction {	
	public SlowDown(CoxAgent cox) {
		super(cox);
	}
	@Override
	public void doExecute() {
		boat.shiftDown();
	}

}
