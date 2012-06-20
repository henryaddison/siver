package siver;


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
/** 
 * BoatAgent is a dumb agent, at each step it will carry on moving in the direction it is facing and at the speed it was set to
 * The CoxAgent will make decisions based on the boat's location to alter these speed and angle properties
 * 
 * @author henryaddison
 *
 */
public class BoatAgent {
	/**
	 * The River object that the boat is considered to be on
	 */
	private River river;
	private double angle;
	private CoxAgent cox;
	
	public BoatAgent(River river) {
		
		this.river = river;
		// and points straight up
		this.angle = 0;		
	}
	
	public void launch(NdPoint pt) {
		this.cox = new CoxAgent(this);
		Context<Object> context = ContextUtils.getContext(this);
		context.add(this.cox);
		
		Grid<Object> grid = (Grid) context.getProjection("Simple Grid");
		ContinuousSpace<Object> space = (ContinuousSpace) context.getProjection("Continuous Space");
		
		space.moveTo(this, pt.getX(), pt.getY());
		grid.moveTo(this, (int)pt.getX(), (int)pt.getY());
	}
	
	// Schedule the step method for agents.  The method is scheduled starting at 
	// tick one with an interval of 1 tick.  Specifically, the step starts at 1, and
	// and recurs at 2,3,4,...etc
	@ScheduledMethod(start = 1, interval = 1, shuffle=true, priority=1)
	public void step() {
		Context<Object> context = ContextUtils.getContext(this);
		Grid<Object> grid = (Grid) context.getProjection("Simple Grid");
		ContinuousSpace<Object> space = (ContinuousSpace) context.getProjection("Continuous Space");
		
		space.moveByVector(this, 1, angle, 0);
		NdPoint myPoint = space.getLocation(this);
		grid.moveTo(this, (int)myPoint.getX(), (int)myPoint.getY());
		
		space.moveTo(cox, myPoint.getX(), myPoint.getY());
		grid.moveTo(cox, (int)myPoint.getX(), (int)myPoint.getY());
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public double getAngle() {
		return angle;
	}
	
	/**
	 * Get the River object the boat is considered to be on
	 * 
	 * @return this.river
	 * @see siver.river.River
	 */
	public River getRiver() {
		return river;
	}
}
