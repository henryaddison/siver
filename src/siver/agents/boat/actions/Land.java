package siver.agents.boat.actions;

import siver.agents.boat.*;

public class Land extends Action {
	
	public Land(CoxAgent cox) {
		super(cox);
	}
	
	@Override
	public void execute() {
		boat.land();
	}

}
