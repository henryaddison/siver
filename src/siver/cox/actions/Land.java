package siver.cox.actions;

import siver.cox.Cox;

public class Land extends SingleTickAction {
	
	public Land(Cox cox) {
		super(cox);
	}
	
	@Override
	public void doExecute() {
		cox.land();
	}

}
