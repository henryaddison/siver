package siver.agents.boat.actions;

import siver.agents.boat.CoxAgent;

public class SlowDown extends Action {
	
	private static final double decrement = -1;
	
	public SlowDown(CoxAgent cox) {
		super(cox);
	}
	@Override
	public void execute() {
		boat.alterSpeed(decrement);
	}

}
