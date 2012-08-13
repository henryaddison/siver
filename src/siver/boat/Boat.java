package siver.boat;


import java.awt.Color;
import java.awt.geom.Point2D;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import siver.context.SiverContextCreator;
import siver.cox.Cox;
import siver.experiments.BoatRecord;
import siver.river.River;

/** 
 * BoatAgent is a dumb agent, at each step it will carry on moving in the direction it is facing and at the speed it was set to
 * The CoxAgent will make decisions based on the boat's location to alter these speed and angle properties
 * 
 * @author henryaddison
 *
 */
public class Boat {
	
	private static final int MAX_GEAR  = 10;
	
	//the river the boat is on and the cox in the boat
	private River river;
	private Cox cox;
	
	//the current gear and orientation
	private double orientation;
	private int gear;
	private double gearMultiplier;
	
	//keep a reference of the space the boat is in for easier movement
	private ContinuousSpace<Object> space;
	private Context<Object> context;
	
	//to keep track of statistics about boat
	private BoatRecord record; 
	
	public Boat(River river, Context<Object> context, ContinuousSpace<Object> space, double gearMult) {
		this.river = river;
		this.space = space;
		this.context = context;
		this.gearMultiplier = gearMult;
	}
	
	public void launch(Cox cox, Integer launch_schedule_id) {
		//initially the boat points straight up and is going at speed 10
		this.orientation = 0;
		this.gear = 0;
		this.cox = cox;
		this.record = new BoatRecord(launch_schedule_id, SiverContextCreator.getTickCount(), this, cox);
	}
	
	public void land() {
		context.remove(this);
		if(this.record != null) {
			this.record.landed(SiverContextCreator.getTickCount());
		}
	}
	
	//MOVEMENT
	@ScheduledMethod(start = 1, interval = 1, priority=10)
	public void run() {
		cox.getNavigator().continueForward();
		record.updateStats(total_distance_covered(), getGear());
	}
	
	public void move(double dist) {
		move(dist, orientation);
	}
	
	public void move(double dist, double heading) {
		space.moveByVector(this, dist, heading, 0);
	}
	
	public void moveTo(NdPoint pt) {
		space.moveTo(this, pt.toDoubleArray(null));
	}
	
	public void steerToward(Point2D.Double pt) {
		NdPoint otherPoint = new NdPoint(pt.getX(), pt.getY());
		double angle = SpatialMath.calcAngleFor2DMovement(space, getLocation(), otherPoint);
		setAngle(angle);
	}
	
	public void deadStop() {
		setGear(0);
	}
	
	//GETTERS AND SETTERS
	public double getAngle() {
		return orientation;
	}
	
	public void setAngle(double angle) {
		this.orientation = angle;
	}
	
	public int getGear() {
		return gear;
	}
	
	public void setGear(int newValue) {
		if(newValue < 0) {
			gear = 0;
		} else if(newValue > MAX_GEAR) {
			gear = 10;
		} else {
			gear = newValue;
		}
		
	}
	
	public double getSpeedMultiplier() {
		return gearMultiplier;
	}
	
	public void shiftUp() {
		setGear(gear+1);
	}
	
	public void shiftDown() {
		setGear(gear-1);
	}
	
	public double getSpeed() {
		return gear*gearMultiplier;
	}
	
	public NdPoint getLocation() {
		return space.getLocation(this);
	}
	
	public ContinuousSpace<Object> getSpace() {
		return space;
	}
	
	public Context<Object> getContext() {
		return context;
	}
	
	public River getRiver() {
		return river;
	}
	
	public double total_distance_covered() {
		return cox.getNavigator().getTotalDistanceCovered();
	}
	
	
	private Color colour;
	private static final Color[] available_colours = {Color.RED, Color.YELLOW, Color.GREEN};
	public Color getColour() {
		if(this.colour == null) {
			int colour_index = RandomHelper.nextIntFromTo(0, available_colours.length-1);
			this.colour = available_colours[colour_index];
		}
		return this.colour;
	}
	
}
