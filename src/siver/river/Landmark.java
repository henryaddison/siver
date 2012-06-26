package siver.river;

import java.awt.geom.Point2D;

/**
 * 
 * Landmark represents a pair of points on opposite sides of the river bank.
 * River class will use them in order to define the river's boundaries.
 * CoxAgent objects will use them to navigate the river.
 * 
 * @author henryaddison
 *
 */

public class Landmark {
	private Point2D.Double left;
	private Point2D.Double right;
	
	/**
	 * 
	 * @param l The point on the left bank of the river.
	 * @param r The point on the right bank of the river.
	 */
	public Landmark(Point2D.Double l, Point2D.Double r) {
		left = l;
		right = r;
	}
	
	/**
	 * 
	 * @return The point on the left bank for this landmark
	 */
	public Point2D.Double getLeft() {
		return left;
	}
	
	
	/**
	 * 
	 * @return The point on the right bank for this landmark
	 */
	public Point2D.Double getRight() {
		return right;
	}
	
	
	/**
	 * This is used by CoxAgent objects to navigate.
	 * Currently very naive and just returns the mid point between the left and right bank points that make up the Landmark
	 * 
	 * @return a Point2D.Double that corresponds to the half-way point between the two
	 */
	public Point2D.Double getLocation() {
		double midX = (left.getX() + right.getX())/2;
		double midY = (left.getY() + right.getY())/2;
		Point2D.Double pt = new Point2D.Double(midX, midY);
		return pt;
	}
	
	/**
	 * @return true if obj is another Landmark and the left points and the right points of the two Landmark objects being compared are equal.
	 */
	public boolean equals(Object obj) {
		if(obj instanceof Landmark) {
			Landmark other = (Landmark) obj;
			return left.equals(other.getLeft()) && right.equals(other.getRight());
		}
		return false;
	}
}
