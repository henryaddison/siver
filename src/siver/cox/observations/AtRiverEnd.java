package siver.cox.observations;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.cox.Cox;
import siver.cox.CoxObservations;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class AtRiverEnd extends BooleanObservation {

	

	public AtRiverEnd(CoxObservations obs, Cox cox, Boat boat,
			BoatNavigation nav) {
		super(obs, cox, boat, nav);
		
	}

	@Override
	protected void calculateValue() {
		LaneNode node = navigator.getDestinationNode();
		LaneEdge next_edge = node.getLane().getNextEdge(node, navigator.headingUpstream());
		value = (next_edge == null);
	}

}
