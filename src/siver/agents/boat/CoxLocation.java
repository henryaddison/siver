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
	
	//The cox object this location refers to
	private CoxAgent cox;
	
	public CoxLocation(CoxAgent cox, LaneEdge<LaneNode> e, boolean up) {
		this.cox = cox;
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
		if(current_edge != null) current_edge.removeCox(cox);
		new_edge.addCox(cox);
		current_edge = new_edge;
		till_edge_end = new_edge.getWeight();
	}
	
	public void moveToEdgeEnd() {
		till_edge_end = 0;
	}
	
	public void moveAlongEdge(double distance) {
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
}

