package siver;

import repast.simphony.space.grid.GridPoint;

public class Landmark {
	private GridPoint left, right;
	
	public Landmark(GridPoint l, GridPoint r) {
		left = l;
		right = r;
	}
	
	public GridPoint getLeft() {
		return left;
	}
	
	public GridPoint getRight() {
		return right;
	}
	
	public GridPoint getLocation() {
		int midX = (left.getX() + right.getX())/2;
		int midY = (left.getY() + right.getY())/2;
		GridPoint pt = new GridPoint(midX, midY);
		return pt;
	}
}
