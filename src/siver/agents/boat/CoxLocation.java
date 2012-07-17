package siver.agents.boat;

import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class CoxLocation {
	// The part of a the lane the cox is currently travelling on.
	private LaneEdge<LaneNode> current_edge;
	
	// How much of the current edge is left before the cox reaches the end and then next node.
	private double till_edge_end;
	
	// Whether the cox is trying to go upstream or downstream.
	private boolean upstream;
	
	// Reference to the node the boat is on, if it is on one otherewise null
	private LaneNode onNode;
	
	public CoxLocation(LaneEdge<LaneNode> e, double tee, boolean up) {
		upstream = up;
		updateEdge(e);
		till_edge_end = tee;
	}
	
	public CoxLocation(LaneEdge<LaneNode> e, boolean up) {
		upstream = up;
		updateEdge(e);
	}
	
	public Lane getLane() {
		return current_edge.getSource().getLane();
	}
	
	public LaneEdge<LaneNode> getEdge() {
		return current_edge;
	}
	
	public double getTillEdgeEnd() {
		return till_edge_end;
	}
	
	public void updateEdge(LaneEdge<LaneNode> new_edge) {
		current_edge = new_edge;
		till_edge_end = new_edge.getWeight();
		onNode = null;
	}
	
	public void moveToEdgeEnd() {
		onNode = getDestinationNode();
		till_edge_end = 0;
	}
	
	public void moveAlongEdge(double distance) {
		onNode = null;
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
	
	public LaneNode getNode() {
		return onNode;
	}
}

