package siver.cox.observations;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.cox.Cox;
import siver.cox.CoxObservations;
import siver.river.River.NoLaneFound;
import siver.river.lane.Lane;


public class BehindLeftLane extends BehindLaneObservation {

	public BehindLeftLane(CoxObservations obs, Cox cox,
			Boat boat, BoatNavigation nav) {
		super(obs, cox, boat, nav);
	}

	@Override
	protected Lane getLane() throws NoLaneFound {
		return getLeftLane();
	}

}
