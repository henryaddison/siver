package siver.agents.boat;

import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class CoxLocation {
	// The part of a the lane the cox is currently travelling on.
	private LaneEdge<LaneNode> current_edge;
	
	// How much of the current edge is left before the cox reaches the end and then next node.
	private double till_edge_end;
	
	public CoxLocation(LaneEdge<LaneNode> e, double tee) {
		updateEdge(e);
	}
	
	public CoxLocation(LaneEdge<LaneNode> e) {
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
	}
	
	public void moveToEdgeEnd() {
		till_edge_end = 0;
	}
	
	public void moveAlongEdge(double distance) {
		till_edge_end -= distance;
	}
}

