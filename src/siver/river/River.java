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
	private ArrayList<Landmark> bank = new ArrayList<Landmark>();
	private Path2D.Double bank_path = new Path2D.Double();
	
	/**
	 * 
	 * This will add a Landmark to the River's list of Landmarks.
	 * 
	 * @param l The Landmark object to add to the river's definition.
	 */
	public void add(Landmark l) {
		bank.add(l);
	}
	
	
	
	/**
	 * Once all Landmarks have been added to the River, call complete to form the Path2D.Double that makes up the River's outline.
	 */
	public void complete() {
		bank_path = new Path2D.Double();
		if(bank.size() > 0) {
			bank_path.moveTo(bank.get(0).getRight().getX(), bank.get(0).getRight().getY());
			for(Landmark l : bank) {
				bank_path.lineTo(l.getRight().getX(), l.getRight().getY());
			}
			
			Collections.reverse(bank);
			for(Landmark l : bank) {
				bank_path.lineTo(l.getLeft().getX(), l.getLeft().getY());
			}
			Collections.reverse(bank);
			
			bank_path.closePath();
		}
	}
	
	/**
	 * Checks whether a given point is on the River.
	 * 
	 * @param x x coordinate of point to check
	 * @param y y coordinate of point to check
	 * @return true if the point is on the River
	 */
	public boolean contains(double x, double y) {
		return bank_path.contains(x,y);
	}
	
	/**
	 * Checks whether a given point is on the River.
	 * 
	 * @param pt Point2D.Double to check
	 * @return true if the point is on the River
	 */
	public boolean contains(Point2D.Double pt) {
		return bank_path.contains(pt);
	}
	
	/**
	 * Get the Landmarks that define the river
	 * 
	 * @return the list of Landmarks that make up the river 
	 */
	public ArrayList<Landmark> getLandmarks() {
		return bank;
	}
}
