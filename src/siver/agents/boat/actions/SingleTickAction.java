package siver.agents.boat.actions;

import siver.agents.boat.Cox;

public abstract class SingleTickAction extends Action {

	public SingleTickAction(Cox cox) {
		super(cox);
	}

	@Override
	public boolean typeSpecificExecute() {
		doExecute();
		return true;
	}
	
	public abstract void doExecute();

}
