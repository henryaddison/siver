package siver;
// CoxAgent will use the boat it is attached to in order to decide how to alter it's
import java.awt.geom.Point2D;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.util.ContextUtils;
import siver.river.Landmark;

public class CoxAgent {
	private BoatAgent boat;
	private int landmark_index;
	private boolean upstream;
	
	public CoxAgent(BoatAgent boat) {
		this.boat = boat;
		// initially the cox wants to head downstream
		this.upstream = false;
		// and towards the 1st landmark
		this.landmark_index = 1;
		
	}
	
	@ScheduledMethod(start = 1, interval = 1, shuffle=true, priority=10)
	public void step() {
		if(nearEndRiver()) {
			spin();
			return;
		}
		if(nearLandmark()) {
			chooseNextLandmark();
			return;
		} 
		if(true) {
			aimToward(getLandmark().getLocation());
			return;
		}
	}
	
	/*
	 * PREDICATES
	 */
	private boolean nearLandmark() {
		return distanceToLandmark() < 5;
	}
	
	
	private boolean nearEndRiver() {
		if(upstream && landmark_index <= 0){
			return distanceToLandmark() < 15;
		}
		if(landmark_index >= boat.getRiver().getLandmarks().size()-1) {
			return distanceToLandmark() < 15;
		}
		return false;
	}	
	
	/*
	 * ACTIONS
	 */
	private void chooseNextLandmark() {
		if(upstream) {
			landmark_index--;
			return;
		}
		if(true){
			landmark_index++;
			return;
		}
	}
	
	private void spin() {
		upstream = !upstream;
		chooseNextLandmark();
	}
	
	private void aimToward(Point2D.Double pt) {
		Context<Object> context = ContextUtils.getContext(this);
		ContinuousSpace<Object> space = (ContinuousSpace) context.getProjection("Continuous Space");
		
		NdPoint myPoint  = space.getLocation(boat);
		NdPoint otherPoint = new NdPoint(pt.getX(), pt.getY());
		double angle = SpatialMath.calcAngleFor2DMovement(space, myPoint, otherPoint);
		boat.setAngle(angle);
	}
	
	/*
	 * HELPERS
	 */
	private double distanceToLandmark() {
		
		return getLandmark().getLocation().distance(boat.getLocation().getX(), boat.getLocation().getY());
	}
	
	private Landmark getLandmark() {
		return boat.getRiver().getLandmarks().get(landmark_index);
	}
}
