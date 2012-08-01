package siver.agents.boat;

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
	
	public BoatNavigation(Cox cox, Boat boat, LaneEdge e, boolean up) {
		this.cox = cox;
		this.boat = boat;
		upstream = up;
		updateEdge(e);
	}
	
	public Lane getLane() {
		return current_edge.getSource().getLane();
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
		till_edge_end = 0;
	}
	
	public void moveAlongEdge(double distance) {
		boat.move(distance);
		till_edge_end -= distance;
	}
	
	public boolean headingUpstream() {
		return upstream;
	}
	
	public void toggleUpstream() {
		upstream = !upstream;
	}
	
	public LaneNode getDestinationNode() {
		return current_edge.getNextNode(upstream);
	}
	
	public boolean changingLane() {
		return (current_edge instanceof LaneChangeEdge);
	}
}

