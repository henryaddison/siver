package siver.cox.observations;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.cox.Cox;
import siver.cox.CoxObservations;

public abstract class BooleanObservation extends AbstractObservation {
	public BooleanObservation(CoxObservations obs, Cox cox, Boat boat, BoatNavigation nav) {
		super(obs, cox, boat, nav);
	}

	@Override
	public Boolean getValue() {
		if(value == null) {
			calculateValue();
		}
		return (Boolean) value;
	}
}
