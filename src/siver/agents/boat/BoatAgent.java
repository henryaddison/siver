package siver.agents.boat;


import java.awt.geom.Point2D;

import repast.simphony.context.Context;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import siver.river.River;
import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

/** 
 * BoatAgent is a dumb agent, at each step it will carry on moving in the direction it is facing and at the speed it was set to
 * The CoxAgent will make decisions based on the boat's location to alter these speed and angle properties
 * 
 * @author henryaddison
 *
 */
public class BoatAgent {
	
	private static final int MAX_GEAR  = 10;
	
	//the river the boat is on and the cox in the boat
	private River river;
	private CoxAgent cox;
	
	//the current gear and orientation
	private double angle;
	private int gear;
	private double gearMultiplier;
	
	//keep a reference of the space the boat is in for easier movement
	private ContinuousSpace<Object> space;
	private Context<Object> context;
	
	public BoatAgent(River river, Context<Object> context, ContinuousSpace<Object> space, double gearMult) {
		this.river = river;
		this.space = space;
		this.context = context;
		this.gearMultiplier = gearMult;
	}
	
	public void launch(CoxAgent cox, Point2D.Double pt) {
		//initially the boat points straight up and is going at speed 10
		this.angle = 0;
		this.gear = 0;
		this.cox = cox;
		
		space.moveTo(this, pt.getX(), pt.getY());
	}
	
	public void land() {
		context.remove(cox);
		context.remove(this);
	}
	
	//MOVEMENT
	
	public void run() {
		CoxLocation location = cox.getLocation();
		double distance_till_next_node = location.getTillEdgeEnd();
		double distance_can_travel = cox.getTickDistanceRemaining();
		if(distance_can_travel >= distance_till_next_node) {
			this.move(distance_till_next_node);
			location.moveToEdgeEnd();
			cox.setTickDistanceRemaining(distance_can_travel - distance_till_next_node);
			
			LaneNode steer_from = location.getDestinationNode();
			Lane lane = steer_from.getLane();
			LaneEdge<LaneNode> next_edge = lane.getNextEdge(steer_from, location.headingUpstream());
			
			location.updateEdge(next_edge);
			this.steerToward(location.getDestinationNode().getLocation());
			run();
		} else {
			this.move(distance_can_travel);
			location.moveAlongEdge(distance_can_travel);
			cox.setTickDistanceRemaining(0);
		}
	}
	
	public void move(double dist) {
		space.moveByVector(this, dist, angle, 0);
	}
	
	public void steerToward(Point2D.Double pt) {
		NdPoint otherPoint = new NdPoint(pt.getX(), pt.getY());
		double angle = SpatialMath.calcAngleFor2DMovement(space, getLocation(), otherPoint);
		setAngle(angle);
	}
	
	//GETTERS AND SETTERS
	public double getAngle() {
		return angle;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
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
	
	public River getRiver() {
		return river;
	}
	
}
