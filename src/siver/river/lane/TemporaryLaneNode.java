package siver.river.lane;

import repast.simphony.space.continuous.NdPoint;

public class TemporaryLaneNode extends LaneNode {
	public TemporaryLaneNode(double x, double y, Lane lane) {
		super(x,y,lane, false);
	}
	
	public TemporaryLaneNode(NdPoint pt, Lane lane) {
		super(pt,lane, false);
	}
	
	@Override
	public boolean isTemporary() {
		return true;
	}
}
