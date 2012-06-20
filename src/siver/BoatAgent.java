package siver;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import siver.river.Landmark;
import siver.river.River;

public class BoatAgent {
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private boolean upstream;
	private int landmark_index;
	private River river;
	private double angle;
	public BoatAgent(ContinuousSpace<Object> space, Grid<Object> grid, River river) {
		this.space = space;
		this.grid = grid;
		this.upstream = false;
		this.river = river;
		this.landmark_index = 1;
		this.angle = 0;
	}
	
	// Schedule the step method for agents.  The method is scheduled starting at 
	// tick one with an interval of 1 tick.  Specifically, the step starts at 1, and
	// and recurs at 2,3,4,...etc
	@ScheduledMethod(start = 1, interval = 1, shuffle=true)
	public void step() {
		if(nearLandmark()) {
			chooseNextLandmark();
			return;
		} 
		if(true) {
			Landmark l = river.getLandmarks().get(landmark_index);
			moveToward(l.getLocation());
			return;
		}
	}
	
	private void moveToward(GridPoint pt) {
		NdPoint myPoint  = space.getLocation(this);
		NdPoint otherPoint = new NdPoint(pt.getX(), pt.getY());
		angle = SpatialMath.calcAngleFor2DMovement(space, myPoint, otherPoint);
		space.moveByVector(this, 1, angle, 0);
		myPoint = space.getLocation(this);
		grid.moveTo(this, (int)myPoint.getX(), (int)myPoint.getY());
	}
	
	private void chooseNextLandmark() {
		if(upstream && landmark_index > 0) {
			landmark_index--;
			return;
		}
		if(upstream && landmark_index <= 0){
			spin();
			return;
		}
		if(landmark_index >= river.getLandmarks().size()-1) {
			spin();
			return;
		}
		if(true){
			landmark_index++;
			return;
		}
	}
	
	private void spin() {
		upstream = !upstream;
	}
	
	private boolean nearLandmark() {
		Landmark l = river.getLandmarks().get(landmark_index);
		double distance = (Math.pow((l.getLocation().getX()-grid.getLocation(this).getX()), 2)+
		Math.pow((l.getLocation().getY()-grid.getLocation(this).getY()), 2));
		return distance < 5;
	}
	
	public double getAngleInDegrees() {
		return -(angle*180.0/Math.PI - 90.0);
	}
}
