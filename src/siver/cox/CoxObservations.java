package siver.cox;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.river.River.NoLaneFound;
import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class CoxObservations {
	private Cox cox;
	private Boat boat;
	private BoatNavigation navigator;
	private static final int VIEWING_DISTANCE = 3;
	private static final double OVERTAKING_SPEED_DIFFERENCE = 1.0;
	
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
	
	public boolean aboveDesiredSpeed() {
		return boat.getGear() > cox.desired_gear();
	}
	
	public int boatGear() {
		return boat.getGear();
	}
	
	public boolean nearbyBoatInfront() {
		return (nearbyOccupiedEdge(navigator.getDestinationNode(), navigator.headingUpstream()) != null);
	}
	
	private LaneEdge nearbyOccupiedEdge(LaneNode node, boolean infront) {
		int edges_ahead = 1;
		while(edges_ahead <= VIEWING_DISTANCE) {
			LaneEdge edge = node.getLane().getNextEdge(node, infront);
			//if there is no further edge then there is no boat in front in viewing distance (though there is the end of the river)
			if(edge == null) return null;
			//as soon as we find an empty edge in front we can return false
			if(!edge.isEmpty()) return edge;
			//otherwise move on to next edge
			node = edge.getNextNode(infront);
			edges_ahead++;
		}
		return null;
	}
	
	public boolean slowBoatInfront() {
		if(!nearbyBoatInfront()) {
			// return false is there is no boat at all in front
			return false;
		}
		
		LaneEdge occupiedEdgeAhead = nearbyOccupiedEdge(navigator.getDestinationNode(), navigator.headingUpstream());
		
		for(Cox coxInfront : occupiedEdgeAhead.getCoxes()) {			
			if(cox.getNavigator().headingUpstream() == coxInfront.getNavigator().headingUpstream()) {
				//if boats are travelling in the same direction then want the difference in their speeds to be greater than limit
				return (Math.abs(boat.getSpeed() - coxInfront.getBoat().getSpeed()) > OVERTAKING_SPEED_DIFFERENCE);
			} else {
				//if boats are travelling in the different direction then want the sum of their speeds to be greater than limit
				return (Math.abs(boat.getSpeed()) + Math.abs(coxInfront.getBoat().getSpeed()) > OVERTAKING_SPEED_DIFFERENCE);
			}
			
		}
		return false;
	}
	
	public boolean laneToLeftIsClear() {
		try {
			Lane laneToLeft = boat.getRiver().getLaneToLeftOf(navigator.getLane(), navigator.headingUpstream());
			return laneIsClear(laneToLeft);
		} catch (NoLaneFound e) {
			//if there is no lane to the left, then it is not clear
			return false;
		}
	}
	
	public boolean laneToRightIsClear() {
		try {
			Lane laneToRight = boat.getRiver().getLaneToRightOf(navigator.getLane(), navigator.headingUpstream());
			return laneIsClear(laneToRight);
		} catch (NoLaneFound e) {
			//if there is no lane to the left, then it is not clear
			return false;
		}
	}
	
	private boolean laneIsClear(Lane lane) {
		LaneNode nearestNodeInLane = lane.nodeNearest(boat.getLocation());
		//return true only if there's nothing ahead and nothing behind
		//very simplistic at the moment, only checks existence, not relative speed
		return (nearbyOccupiedEdge(nearestNodeInLane, true) == null) && (nearbyOccupiedEdge(nearestNodeInLane, false) == null);
	}
	
	public boolean changingLane() {
		return navigator.changingLane();
	}
	
	public boolean outingOver() {
		return (boat.total_distance_covered() >= cox.getGoalDistance()) && (navigator.getDestinationNode().equals(navigator.getLane().getStartNode()));
	}
}
