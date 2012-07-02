package siver.river.lane;

import java.awt.geom.Point2D;

public class LaneNode {
	protected Point2D.Double location;
	
	public LaneNode(double x, double y) {
		location = new Point2D.Double(x,y);
	}
	
	public LaneNode(Point2D.Double l) {
		location = l;
	}
	
	public double distance(LaneNode other) {
		return location.distance(other.location);
	}
	
	public Point2D.Double getLocation() {
		return location;
	}
}
