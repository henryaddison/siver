package siver.cox.brains;

import siver.cox.CoxObservations;
import siver.cox.actions.Action;

public abstract class CoxBrain {
	protected CoxObservations observations;
	
	public CoxBrain(CoxObservations obs) {
		observations = obs;	
	}
	
	public abstract Class<? extends Action> chooseAction();
}
