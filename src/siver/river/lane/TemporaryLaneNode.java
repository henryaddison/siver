package siver.river.lane;

import repast.simphony.space.continuous.NdPoint;

public class TemporaryLaneNode extends LaneNode {
	public TemporaryLaneNode(double x, double y, Lane lane) {
		super(x,y,lane, Lane.DEFAULT_OPACITY);
	}
	
	public TemporaryLaneNode(NdPoint pt, Lane lane) {
		super(pt,lane, Lane.DEFAULT_OPACITY);
	}
	
	@Override
	public boolean isTemporary() {
		return true;
	}
}
