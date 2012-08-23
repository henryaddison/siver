package siver.river.lane;

import java.awt.geom.Point2D;

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
	private boolean blocksVision;
	
	
	public LaneNode(double x, double y, Lane l, boolean visionBlocker) {
		setupProperties(new Point2D.Double(x,y), l, visionBlocker);
		
	}
	
	public LaneNode(Point2D.Double loc, Lane lane, boolean visionBlocker) {
		setupProperties(loc, lane, visionBlocker);
	}
	
	public LaneNode(NdPoint loc, Lane lane, boolean visionBlocker) {
		setupProperties(new Point2D.Double(loc.getX(), loc.getY()), lane, visionBlocker);
	}
	
	private void setupProperties(Point2D.Double loc, Lane l, boolean visionBlocker) {
		this.location = loc;
		this.lane = l;
		this.blocksVision=visionBlocker;
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
	
	public boolean blocksVision() {
		return blocksVision;
	}
}
