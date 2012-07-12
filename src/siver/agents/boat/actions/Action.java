package siver.agents.boat.actions;

import siver.agents.boat.*;

public abstract class Action {
	protected CoxAgent cox;
	
	public Action(CoxAgent cox) {
		this.cox = cox;
	}
	
	public abstract void execute();
}
