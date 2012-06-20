package siver.river;

import java.awt.Polygon;
import java.util.ArrayList;

import repast.simphony.space.grid.GridPoint;

public class River {
	ArrayList<Landmark> bank = new ArrayList<Landmark>();
	ArrayList<Polygon> polys = new ArrayList<Polygon>();
	
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
