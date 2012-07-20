package siver.agents.boat.actions;

import siver.agents.boat.*;

public class Land extends SingleTickAction {
	
	public Land(CoxAgent cox) {
		super(cox);
	}
	
	@Override
	public void doExecute() {
		boat.land();
	}

}
