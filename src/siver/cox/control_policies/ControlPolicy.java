package siver.cox.control_policies;

import java.lang.reflect.InvocationTargetException;

import siver.cox.CoxObservations;
import siver.cox.actions.Action;
import siver.cox.actions.Land;

public abstract class ControlPolicy {
	CoxObservations latestObservations;
	
	public void updateObservations(CoxObservations obs) {
		latestObservations = obs;
	}
	
	public Class<? extends Action> chooseAction() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		if(latestObservations.outingComplete()) {
			return Land.class;
		}
		if(true) {
			Class<? extends Action> actionClass = typeSpecificActionChoice();
			if(actionClass == null) {
				throw new RuntimeException("No action chosen by brain. Something has gone very wrong");
			}
			return actionClass;
		}
		throw new RuntimeException("No action chosen by brain. Something has gone very wrong");
	}
	
	protected abstract Class<? extends Action> typeSpecificActionChoice() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
