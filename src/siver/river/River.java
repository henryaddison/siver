package siver.river;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * River is used to define a layout of the river.
 * 
 * A River object is defined by a series of Landmark objects.
 * Once all the Landmarks are added, the call complete() to join up the points in a correct order to create a river polygon (using Path2D.Double)
 * 
 * @see java.awt.geom.Path2D
 * @see siver.river.Landmark
 * 
 * @author henryaddison
 *
 */
public class River {
	private Path2D.Double bank = new Path2D.Double();
	
	/**
	 * 
	 * This will add a Landmark to the River's list of Landmarks.
	 * 
	 * @param l The Landmark object to add to the river's definition.
	 */
	public void add(double x, double y) {
		if(bank.getCurrentPoint() == null) {
			bank.moveTo(x, y);
		} else {
			bank.lineTo(x, y);
		}
	}
	
	/**
	 * Once all Landmarks have been added to the River, call complete to form the Path2D.Double that makes up the River's outline.
	 */
	public void complete() {
		bank.closePath();
	}
	
	/**
	 * Checks whether a given point is on the River.
	 * 
	 * @param x x coordinate of point to check
	 * @param y y coordinate of point to check
	 * @return true if the point is on the River
	 */
	public boolean contains(double x, double y) {
		return bank.contains(x,y);
	}
	
	/**
	 * Checks whether a given point is on the River.
	 * 
	 * @param pt Point2D.Double to check
	 * @return true if the point is on the River
	 */
	public boolean contains(Point2D.Double pt) {
		return bank.contains(pt);
	}
	

	
	/**
	 * Get the Path2D.Double outline for the river
	 */
	public Path2D.Double getOutline() {
		return bank;
	}
}
