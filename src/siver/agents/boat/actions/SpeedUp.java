package siver.agents.boat.actions;

import siver.agents.boat.CoxAgent;

public class SpeedUp extends LetBoatRun {
	
	public SpeedUp(CoxAgent cox) {
		super(cox);
	}
	
	private static final double increment = 0.5;
	
	@Override
	public void execute() {
		boat.alterSpeed(increment);
		super.execute();
	}

}
