package siver.cox.observations;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.cox.Cox;
import siver.cox.CoxObservations;
import siver.river.lane.LaneEdge;

public class SlowBoatInfront extends BooleanObservation {
	public SlowBoatInfront(CoxObservations obs, Cox cox, Boat boat,
			BoatNavigation nav) {
		super(obs, cox, boat, nav);
	}

	private static final double OVERTAKING_SPEED_DIFFERENCE = 1.0;

	@Override
	protected void calculateValue() {
		value = calculateSlowBoatInfront();

	}
	
	private boolean calculateSlowBoatInfront() {
		LaneEdge occupiedEdgeAhead = (LaneEdge) getVision().blockedEdge(navigator.getLane(),true);
		if(occupiedEdgeAhead == null) {
			// return false if there is no occupied edge ahead
			return false;
		}
		
		for(Cox coxInfront : occupiedEdgeAhead.getCoxes()) {			
			if(cox.getNavigator().headingUpstream() == coxInfront.getNavigator().headingUpstream()) {
				//if boats are travelling in the same direction then want the difference in their speeds to be greater than limit
				return (boat.getSpeed() - coxInfront.getBoat().getSpeed()) > OVERTAKING_SPEED_DIFFERENCE;
			} else {
				//if boats are travelling in the different direction then want the sum of their speeds to be greater than limit
				return (boat.getSpeed() + coxInfront.getBoat().getSpeed()) > OVERTAKING_SPEED_DIFFERENCE;
			}
			
		}
		return false;
	}

}
