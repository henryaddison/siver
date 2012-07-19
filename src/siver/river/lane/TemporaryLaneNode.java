package siver.river.lane;

import repast.simphony.space.continuous.NdPoint;

public class TemporaryLaneNode extends LaneNode {
	public TemporaryLaneNode(double x, double y, Lane lan) {
		super(x,y,lan);
	}
	
	public TemporaryLaneNode(NdPoint pt, Lane lan) {
		super(pt,lan);
	}
	
	@Override
	public boolean isTemporary() {
		return true;
	}
}
