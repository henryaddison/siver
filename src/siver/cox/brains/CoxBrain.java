package siver.cox.brains;

import siver.cox.CoxObservations;

public abstract class CoxBrain {
	protected CoxObservations observations;
	
	public CoxBrain(CoxObservations obs) {
		observations = obs;	
	}
	
	public abstract Class chooseAction();
}
