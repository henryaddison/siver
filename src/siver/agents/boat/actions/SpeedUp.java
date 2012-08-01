package siver.agents.boat.actions;

import siver.boat.Cox;

public class SpeedUp extends SingleTickAction {
	
	public SpeedUp(Cox cox) {
		super(cox);
	}
	
	@Override
	public void doExecute() {
		boat.shiftUp();
	}

}
