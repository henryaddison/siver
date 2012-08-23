package siver.cox.observations;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.cox.Cox;
import siver.cox.CoxObservations;

public abstract class AheadLaneObservation extends LaneObservation {

	public AheadLaneObservation(CoxObservations obs, Cox cox, Boat boat,
			BoatNavigation nav) {
		super(obs, cox, boat, nav);
	}

	public boolean getInfront() {
		return true;
	}

}
