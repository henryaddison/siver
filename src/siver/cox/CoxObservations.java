package siver.cox;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class CoxObservations {
	private Cox cox;
	private Boat boat;
	private BoatNavigation navigator;
	private static final int VIEWING_DISTANCE = 3;
	
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
	
	public boolean nearbyBoatInfront() {
		LaneNode node = navigator.getDestinationNode();
		boolean upstream = navigator.headingUpstream();
		int edges_ahead = 1;
		while(edges_ahead <= VIEWING_DISTANCE) {
			LaneEdge edge = node.getLane().getNextEdge(node, upstream);
			//if there is no further edge then there is no boat in front in viewing distance (though there is the end of the river)
			if(edge == null) return false;
			//as soon as we find an empty edge in front we can return false
			if(!edge.isEmpty()) return true;
			//otherwise move on to next edge
			node = edge.getNextNode(upstream);
			edges_ahead++;
		}
		
		return false;
	}
	
	public boolean outingOver() {
		return cox.outingOver();
	}
}
