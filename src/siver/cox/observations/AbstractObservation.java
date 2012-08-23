package siver.cox.observations;

import siver.boat.*;
import siver.cox.*;

public abstract class AbstractObservation {
	protected Cox cox;
	protected Boat boat;
	protected BoatNavigation navigator;
	protected Object value;
	protected CoxObservations observations;
	
	public AbstractObservation(CoxObservations obs, Cox cox, Boat boat, BoatNavigation nav) {
		this.cox = cox;
		this.boat = boat;
		this.navigator = nav;
		this.observations = obs;
	}
	
	public abstract Object getValue();
	
	protected abstract void calculateValue();
	
	protected CoxVision getVision() {
		return observations.getVision();
	}
}
