package siver.cox.observations;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.cox.Cox;
import siver.cox.CoxObservations;
import siver.river.lane.Lane;

public class BehindCurrentLane extends BehindLaneObservation {
	public BehindCurrentLane(CoxObservations obs, Cox cox, Boat boat,
			BoatNavigation nav) {
		super(obs, cox, boat, nav);
	}
	
	protected Lane getLane() {
		return getCurrentLane();
	}
}
