package siver.river.lane;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import repast.simphony.space.continuous.NdPoint;
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
	protected Lane lane;
	
	public LaneNode(double x, double y, Lane l) {
		location = new Point2D.Double(x,y);
		lane = l;
	}
	
	public LaneNode(Point2D.Double loc, Lane lan) {
		location = loc;
		lane = lan;
	}
	
	public LaneNode(NdPoint loc, Lane lan) {
		location = new Point2D.Double(loc.getX(), loc.getY());
		lane = lan;
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
	
	/**
	 * 
	 * @return lane node is in
	 */
	public Lane getLane() {
		return lane;
	}
	
	/**
	 * 
	 * @return NdPoint that matches this nodes location
	 */
	public NdPoint toNdPoint() {
		return new NdPoint(location.getX(), location.getY());
	}
	
	public double distance(NdPoint pt) {
		Point2D.Double ptInGeom = new Point2D.Double(pt.getX(), pt.getY());
		return location.distance(ptInGeom);
	}
	
	
	public boolean isTemporary() {
		return false;
	}
}
