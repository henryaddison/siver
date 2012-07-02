package siver.river.lane;

import java.awt.geom.Point2D;
/**
 * The base class for the nodes in a Lane network.
 * Upon reaching a node, a cox will make a decision based on the information provided by the node.
 * This is a way of modelling a cox's vision.
 * 
 * @author henryaddison
 *
 */
public class LaneNode {
	// a node has a location in the world
	protected Point2D.Double location;
	
	public LaneNode(double x, double y) {
		location = new Point2D.Double(x,y);
	}
	
	public LaneNode(Point2D.Double l) {
		location = l;
	}
	
	/**
	 * 
	 * @param other the LaneNode that should be measured to
	 * @return the Euclidean distance between two nodes
	 */
	public double distance(LaneNode other) {
		return location.distance(other.location);
	}
	
	/**
	 * 
	 * @return location of the node
	 */
	public Point2D.Double getLocation() {
		return location;
	}
}
