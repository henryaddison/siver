package siver.agents.boat.actions;

import siver.agents.boat.*;

public abstract class Action {
	protected CoxAgent cox;
	protected BoatAgent boat;
	protected CoxLocation location;
	
	public Action(CoxAgent cox) {
		this.cox = cox;
		this.boat = cox.getBoat();
		this.location = cox.getLocation();
	}
	
	public abstract void execute();
}
