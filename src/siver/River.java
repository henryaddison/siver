package siver;

import java.awt.Polygon;
import java.util.ArrayList;

import repast.simphony.space.grid.GridPoint;

public class River {
	ArrayList<GridPoint> rightbank = new ArrayList<GridPoint>();
	ArrayList<GridPoint> leftbank = new ArrayList<GridPoint>();
	ArrayList<Polygon> polys = new ArrayList<Polygon>();
	
	public void add(GridPoint left, GridPoint right) {
		leftbank.add(left);
		rightbank.add(right);
		
		if(rightbank.size() > 1) {
			int size = rightbank.size();
			int[] xcoords = {rightbank.get(size-2).getX(),
							rightbank.get(size-1).getX(), 
							leftbank.get(size-1).getX(), 
							leftbank.get(size-2).getX()};
			int[] ycoords = {rightbank.get(size-2).getY(),
					rightbank.get(size-1).getY(), 
					leftbank.get(size-1).getY(), 
					leftbank.get(size-2).getY()};
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
}
