package siver.cox.observations;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.cox.Cox;
import siver.cox.CoxObservations;

public abstract class IntegerObservation extends AbstractObservation {

	public IntegerObservation(CoxObservations obs, Cox cox, Boat boat,
			BoatNavigation nav) {
		super(obs, cox, boat, nav);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Integer getValue() {
		if(value == null) {
			calculateValue();
		}
		return (Integer) value;
	}

	

}
