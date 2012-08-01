package siver.agents.boat;

public abstract class CoxBrain {
	protected CoxObservations observations;
	
	public CoxBrain(CoxObservations obs) {
		observations = obs;	
	}
	
	public abstract Class chooseAction();
}
