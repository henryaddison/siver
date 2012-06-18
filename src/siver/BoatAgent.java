package siver;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

public class BoatAgent {
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private boolean upstream;
	public BoatAgent(ContinuousSpace<Object> space, Grid<Object> grid) {
		this.space = space;
		this.grid = grid;
		this.upstream = false;
	}
	
	// Schedule the step method for agents.  The method is scheduled starting at 
	// tick one with an interval of 1 tick.  Specifically, the step starts at 1, and
	// and recurs at 2,3,4,...etc
	@ScheduledMethod(start = 1, interval = 1, shuffle=true)
	public void step() {
		GridPoint myLocation = grid.getLocation(this);
		if(!upstream && myLocation.getY() < 48) {
			GridPoint pt = new GridPoint(15,49);
			moveToward(pt);
		} else if(!upstream && myLocation.getY() >= 48) {
			spin();
		} else if(upstream && myLocation.getY() <= 1) {
			spin();
		} else if(upstream && myLocation.getY() > 1){
			GridPoint pt = new GridPoint(15,0);
			moveToward(pt);
		}
	}


	private void spin() {
		upstream = !upstream;
	}
	
	private void moveToward(GridPoint pt) {
		NdPoint myPoint  = space.getLocation(this);
		NdPoint otherPoint = new NdPoint(pt.getX(), pt.getY());
		double angle = SpatialMath.calcAngleFor2DMovement(space, myPoint, otherPoint);
		space.moveByVector(this, 1, angle, 0);
		myPoint = space.getLocation(this);
		grid.moveTo(this, (int)myPoint.getX(), (int)myPoint.getY());
	}
}
