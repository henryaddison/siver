/**
 * 
 */
package siver.river;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author henryaddison
 *
 */
public abstract class OutlinedArea {
	//the list of points defining the top and bottom 
	protected ArrayList<Point2D.Double> top, bottom;
	
	//outline of the lane to be used to display the lane
	protected Path2D.Double outline;
	
	// used to determine whether this outlined area has been completed as 
	// should not change a completed outlined area
	protected boolean completed = false;
	
	/**
	 * Call when an outlined area has been finished and will no longer be extended.
	 * 
	 * Allows the outline of the area to be drawn.
	 */
	public void complete() {
		outline = new Path2D.Double();
		outline.moveTo(top.get(0).getX(), top.get(0).getY());
		for(Point2D.Double pt : top) {
			outline.lineTo(pt.getX(), pt.getY());
		}
		
		Collections.reverse(bottom);
		for(Point2D.Double pt : bottom) {
			outline.lineTo(pt.getX(), pt.getY());
		}
		Collections.reverse(bottom);
		
		outline.closePath();
		
		completed = true;
	}
	
	
	//CHECKERS
	
	/**
	 * Indicates whether the lane has been completed
	 */
	public boolean isComplete() {
		return completed;
	}
	
	
	/**
	 * Checks whether a given point is within the outlined area.
	 * 
	 * @param x x coordinate of point to check
	 * @param y y coordinate of point to check
	 * @return true if the point is within the outlined area
	 */
	public boolean contains(double x, double y) {
		return outline.contains(x,y);
	}
	
	/**
	 * Checks whether a given point is within the outlined area.
	 * 
	 * @param pt Point2D.Double to check
	 * @return true if the point is within the outlined area
	 */
	public boolean contains(Point2D.Double pt) {
		return outline.contains(pt);
	}
	
	//GETTERS & SETTERS
	
	/**
	 * 
	 * Gets the points that make up the Lane#s top boundary.
	 * 
	 * Top and bottom are somewhat arbitrary which fits well with the arbitrary 
	 * way the lane edges must be differentiated.
	 * 
	 * @return An ArrayList of Point2D.Double that defines the top boundary ().
	 */
	public ArrayList<Point2D.Double> getTop() {
		return top;
	}
	
	/**
	 * 
	 * Gets the points that make up the Lane's bottom boundary.
	 * 
	 * Top and bottom are somewhat arbitrary which fits well with the arbitrary 
	 * way the lane edges must be differentiated.
	 * 
	 * @return An ArrayList of Point2D.Double that defines the bottom boundary ().
	 */
	public ArrayList<Point2D.Double> getBottom() {
		return bottom;
	}
	
	/**
	 * Get the Path2D.Double outline for the lane
	 */
	public Path2D.Double getOutline() {
		return outline;
	}
}
