package siver.cox.actions;

import siver.boat.*;
import siver.cox.Cox;

public abstract class Action {
	protected Cox cox;
	protected Boat boat;
	protected BoatNavigation location;
	
	public Action(Cox cox) {
		this.cox = cox;
		this.boat = cox.getBoat();
		this.location = cox.getNavigator();
	}
	
	public void execute() {
		if(typeSpecificExecute()) {
			cox.clearAction();
		}
	}
	
	
	public abstract boolean typeSpecificExecute();
}
