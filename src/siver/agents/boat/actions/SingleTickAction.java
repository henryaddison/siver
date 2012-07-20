package siver.agents.boat.actions;

import siver.agents.boat.CoxAgent;

public abstract class SingleTickAction extends Action {

	public SingleTickAction(CoxAgent cox) {
		super(cox);
	}

	@Override
	public boolean typeSpecificExecute() {
		doExecute();
		return true;
	}
	
	public abstract void doExecute();

}
