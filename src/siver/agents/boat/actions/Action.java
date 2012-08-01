package siver.agents.boat.actions;

import siver.agents.boat.*;

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
