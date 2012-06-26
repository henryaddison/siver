package siver.river;

import java.awt.Polygon;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Collections;

import repast.simphony.space.grid.GridPoint;
/**
 * 
 * River is used to define a layout of the river.
 * 
 * A River object is defined by a series of Landmark objects. Each consecutive pair of Landmark objects are joined up to form a Polygon.
 * A River object can be considered a series of these Polygons.
 * 
 * @see java.awt.Polygon
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
	 * This will...
	 * 
	 * @param l The Landmark object to add to the river's definition.
	 */
	public void add(Landmark l) {
		bank.add(l);
	}
	
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
	
	public boolean contains(int x, int y) {
		return bank_path.contains(x,y);
	}
	
	public ArrayList<Landmark> getLandmarks() {
		return bank;
	}
}
