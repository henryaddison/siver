package siver.cox.actions;

import siver.cox.Cox;

public class SpeedUp extends SingleTickAction {
	
	public SpeedUp(Cox cox) {
		super(cox);
	}
	
	@Override
	public void doExecute() {
		boat.shiftUp();
	}

}
