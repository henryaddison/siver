package siver.boat;

import siver.cox.Cox;
import siver.river.lane.Lane;
import siver.river.lane.LaneChangeEdge;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class BoatNavigation {
	// The part of a the lane the cox is currently travelling on.
	private LaneEdge current_edge;
	
	// How much of the current edge is left before the cox reaches the end and then next node.
	private double till_edge_end;
	
	// Whether the cox is trying to go upstream or downstream.
	private boolean upstream;
	
	//The cox object this location refers to
	private Cox cox;
	
	private Boat boat;
	
	private double tick_distance_remaining;
	private double total_distance_covered;
	
	public BoatNavigation(Cox cox, Boat boat, boolean up) {
		this.cox = cox;
		this.boat = boat;
		upstream = up;
	}
	
	public void launch(Lane launchLane) {
		total_distance_covered = 0;
		
		//place the boat at the location of the first node of the lane
		LaneNode launchNode = launchLane.getStartNode();
		boat.moveTo(launchNode.toNdPoint());
		
		//and point the boat in the correct direction
		LaneEdge launchEdge = launchLane.getNextEdge(launchNode, false);
		updateEdge(launchEdge);
	}
	
	public Lane getLane() {
		return getDestinationNode().getLane();
	}
	
	public LaneEdge getEdge() {
		return current_edge;
	}
	
	public double getTillEdgeEnd() {
		return till_edge_end;
	}
	
	public void updateEdge(LaneEdge new_edge) {
		updateEdge(new_edge, true);
	}
	
	public void updateEdge(LaneEdge new_edge, boolean unoccupy_current) {
		if(unoccupy_current && (current_edge != null)) current_edge.removeCox(cox);
		
		new_edge.addCox(cox);
		
		current_edge = new_edge;
		till_edge_end = new_edge.getWeight();
		
		boat.steerToward(getDestinationNode().getLocation());
	}
	
	public void moveToEdgeEnd() {
		boat.move(till_edge_end);
		travelled(till_edge_end);
		till_edge_end = 0;
	}
	
	public void moveAlongEdge(double distance) {
		boat.move(distance);
		till_edge_end -= distance;
		travelled(distance);
	}
	
	public boolean headingUpstream() {
		return upstream;
	}
	
	public void toggleUpstream() {
		//this also means that the distance the boat has to travel is what it had previously used up of the edge 
		till_edge_end = (current_edge.getWeight() - getTillEdgeEnd());
		//then upstream flag gets toggled
		upstream = !upstream;
	}
	
	public LaneNode getDestinationNode() {
		return current_edge.getNextNode(upstream);
	}
	
	public boolean changingLane() {
		return (current_edge instanceof LaneChangeEdge);
	}
	
	public void continueForward() {
		setTickDistanceRemaining(boat.getSpeed());
		moveBoat();
	}

	private void moveBoat() {
		double distance_till_next_node = getTillEdgeEnd();
		if(distance_till_next_node > tick_distance_remaining) {
			moveAlongEdge(tick_distance_remaining);
			setTickDistanceRemaining(0);
		} else {
			moveToEdgeEnd();
			
			setTickDistanceRemaining(getTickDistanceRemaining()-distance_till_next_node);
			LaneNode steer_from = getDestinationNode();
			Lane lane = steer_from.getLane();
			LaneEdge next_edge = lane.getNextEdge(steer_from, headingUpstream());
			
			if(next_edge != null) {
				updateEdge(next_edge);
				moveBoat(); 
			} else {
				// no next edge - assume we have hit the end of the river so stop the boat instantly
				boat.deadStop();
			}
		}
	}
	
	public double getTickDistanceRemaining() {
		return tick_distance_remaining;
	}

	private void setTickDistanceRemaining(double tick_distance_remaining) {
		this.tick_distance_remaining = tick_distance_remaining;
	}

	public double getTotalDistanceCovered() {
		return total_distance_covered;
	}

	private void travelled(double distance) {
		this.total_distance_covered += distance;
	}
}

