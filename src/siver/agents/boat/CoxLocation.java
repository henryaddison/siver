package siver.agents.boat;

import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class CoxLocation {
	// The lane the cox is in.
	private Lane lane;
	
	// The part of a the lane the cox is currently travelling on.
	private LaneEdge<LaneNode> current_edge;
	
	// How much of the current edge is left before the cox reaches the end and then next node.
	private double till_edge_end;
	
	public CoxLocation(Lane l, LaneEdge<LaneNode> e, double tee) {
		lane = l;
		current_edge = e;
		till_edge_end = tee;
	}
	
	public Lane getLane() {
		return lane;
	}
	
	public LaneEdge<LaneNode> getEdge() {
		return current_edge;
	}
	
	public double getTillEdgeEnd() {
		return till_edge_end;
	}
}

