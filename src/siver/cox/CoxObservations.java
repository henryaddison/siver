package siver.cox;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class CoxObservations {
	private Cox cox;
	private Boat boat;
	private BoatNavigation navigator;
	
	public CoxObservations(Cox cox, Boat boat, BoatNavigation navigator) {
		this.cox = cox;
		this.boat = boat;
		this.navigator = navigator;
	}
	
	public boolean atRiversEnd() { 
		LaneNode node = navigator.getDestinationNode();
		LaneEdge next_edge = node.getLane().getNextEdge(node, navigator.headingUpstream());
		return next_edge == null;
	}
	
	public boolean belowDesiredSpeed() {
		return boat.getGear() < cox.desired_gear();
	}
	
	public int boatGear() {
		return boat.getGear();
	}
}