package siver.cox.observations;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.cox.Cox;
import siver.cox.CoxObservations;

public class NearbyBoatInfront extends BooleanObservation {
	public NearbyBoatInfront(CoxObservations obs, Cox cox, Boat boat, BoatNavigation nav) {
		super(obs, cox, boat, nav);
	}

	private static final int CLEAR_BOUNDARY = 3;
	
	@Override
	protected void calculateValue() {
		value = (getVision().edgesOfClearRiver(navigator.getLane(), true) < CLEAR_BOUNDARY) && 
				(getVision().blockedEdge(navigator.getLane(), true) != null);
	}

}
