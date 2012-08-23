package siver.cox.observations;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.cox.Cox;
import siver.cox.CoxObservations;

public class BelowDesiredSpeed extends BooleanObservation {

	public BelowDesiredSpeed(CoxObservations obs, Cox cox, Boat boat,
			BoatNavigation nav) {
		super(obs, cox, boat, nav);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void calculateValue() {
		value = boat.getGear() < cox.desired_gear();
	}

}
