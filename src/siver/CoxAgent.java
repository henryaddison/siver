package siver;
// CoxAgent will use the boat it is attached to in order to decide how to alter it's
import java.awt.geom.Point2D;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;
import siver.river.Landmark;
import siver.river.River;

public class CoxAgent {
	private BoatAgent boat;
	private int landmark_index;
	private boolean upstream;
	
	public CoxAgent(BoatAgent boat) {
		this.boat = boat;
		// and heads to the 1st landmark
		this.landmark_index = 1;
		// initially the boat is moving downstream
		this.upstream = false;
	}
	
	@ScheduledMethod(start = 1, interval = 1, shuffle=true, priority=10)
	public void step() {
		if(nearLandmark()) {
			chooseNextLandmark();
			return;
		} 
		if(true) {
			Landmark l = boat.getRiver().getLandmarks().get(landmark_index);
			aimToward(l.getLocation());
			return;
		}
	}
	
	private boolean nearLandmark() {
		Context<Object> context = ContextUtils.getContext(this);
		Grid<Object> grid = (Grid) context.getProjection("Simple Grid");
		
		Landmark l = boat.getRiver().getLandmarks().get(landmark_index);
		double distance = (Math.pow((l.getLocation().getX()-grid.getLocation(boat).getX()), 2)+
		Math.pow((l.getLocation().getY()-grid.getLocation(boat).getY()), 2));
		return distance < 144;
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
		if(landmark_index >= boat.getRiver().getLandmarks().size()-1) {
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
	
	private void aimToward(Point2D.Double pt) {
		Context<Object> context = ContextUtils.getContext(this);
		Grid<Object> grid = (Grid) context.getProjection("Simple Grid");
		ContinuousSpace<Object> space = (ContinuousSpace) context.getProjection("Continuous Space");
		
		NdPoint myPoint  = space.getLocation(boat);
		NdPoint otherPoint = new NdPoint(pt.getX(), pt.getY());
		double angle = SpatialMath.calcAngleFor2DMovement(space, myPoint, otherPoint);
		boat.setAngle(angle);
	}
}
