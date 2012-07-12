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
		current_edge = e;
		till_edge_end = tee;
	}
	
	public CoxLocation(LaneEdge<LaneNode> e) {
		current_edge = e;
		till_edge_end = e.getWeight();
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
}

