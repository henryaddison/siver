package siver.boat.actions;

import siver.cox.Cox;

public class SlowDown extends SingleTickAction {	
	public SlowDown(Cox cox) {
		super(cox);
	}
	@Override
	public void doExecute() {
		boat.shiftDown();
	}

}
