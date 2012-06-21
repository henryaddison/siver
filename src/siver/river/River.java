package siver.river;

import java.awt.Polygon;
import java.util.ArrayList;

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
	private ArrayList<Polygon> polys = new ArrayList<Polygon>();
	
	/**
	 * 
	 * This will...
	 * 
	 * @param l The Landmark object to add to the river's definition.
	 */
	public void add(Landmark l) {
		bank.add(l);
		
		if(bank.size() > 1) {
			int size = bank.size();
			int[] xcoords = {bank.get(size-2).getRight().getX(),
							bank.get(size-1).getRight().getX(),
							bank.get(size-1).getLeft().getX(),
							bank.get(size-2).getLeft().getX()};
			int[] ycoords = {bank.get(size-2).getRight().getY(),
							bank.get(size-1).getRight().getY(),
							bank.get(size-1).getLeft().getY(),
							bank.get(size-2).getLeft().getY()};
			Polygon next_bit_of_river = new Polygon(xcoords, ycoords, 4);
			polys.add(next_bit_of_river);
		}
	}
	
	public boolean contains(int x, int y) {
		for(Polygon p : polys) {
			if(p.contains(x,y)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Landmark> getLandmarks() {
		return bank;
	}
}
