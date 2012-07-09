package siver.agents.boat;


import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.util.ContextUtils;
import siver.context.SiverContextCreator;
import siver.river.River;
import siver.river.lane.Lane;
import siver.river.lane.Lane.UnstartedLaneException;
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
	//the river the boat is on and the cox in the boat
	private River river;
	private CoxAgent cox;
	
	//the current speed, orientation and lane of the boat
	private double angle;
	private double speed;
	private Lane lane;
	
	//keep a record of the space the boat is in for easier movement
	private ContinuousSpace<Object> space;
	
	//some debugging variables for checking collision detection 
	private BoatCorner tl,tr,br,bl;
	Point2D.Double blptDst = new Point2D.Double();
	Point2D.Double brptDst = new Point2D.Double();
	Point2D.Double trptDst = new Point2D.Double();
	Point2D.Double tlptDst = new Point2D.Double();
	
	/**
	 * The part of a the lane the boat is currently travelling on
	 */
	private LaneEdge<LaneNode> current_edge;
	
	public BoatAgent(River river, ContinuousSpace<Object> space) {
		this.river = river;
		this.space = space;
	}
	
	public void launch(Lane launchLane) throws UnstartedLaneException {
		//initially the boat points straight up and is going at speed 10
		this.angle = 0;
		this.speed = 10;
		this.lane = launchLane;
		
		//and is positioned on the startNode of the lane
		LaneNode launchNode = lane.getStartNode();
		space.moveTo(this, launchNode.getLocation().getX(), launchNode.getLocation().getY());
		
		current_edge = (LaneEdge<LaneNode>) lane.getNet().getOutEdges(launchNode).iterator().next();
		
		//now add the cox and the 4 corners for collision detection
		Context<Object> context = ContextUtils.getContext(this);
		
		this.cox = new CoxAgent(this);
		context.add(this.cox);
		
		setupCorners();
	}
	
	
	
	//BEHAVIOUR
	
	@ScheduledMethod(start = 1, interval = 1, shuffle=true, priority=10)
	public void step() {
		move(this.speed);
	}
	
	//MOVEMENT
	
	public void move(double dist) {
		space.moveByVector(this, dist, angle, 0);
		
		AffineTransform at = new AffineTransform();
		at.translate(getLocation().getX(), getLocation().getY());
		at.rotate(angle);
		
		at.transform(new Point2D.Double(-8.5,-3.5), blptDst);
		at.transform(new Point2D.Double(8.5,-3.5), brptDst);
		at.transform(new Point2D.Double(-8.5,3.5), tlptDst);
		at.transform(new Point2D.Double(8.5,3.5), trptDst);
	}
	
	//GETTERS AND SETTERS
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public NdPoint getLocation() {
		return space.getLocation(this);
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
	
	
	//COLLISION DETECTION
	private void setupCorners() {
		Context<Object> context = ContextUtils.getContext(this);
		
		tl = new BoatCorner();
		context.add(tl);
		
		tr = new BoatCorner();
		context.add(tr);
		
		br = new BoatCorner();
		context.add(br);
		
		bl = new BoatCorner();
		context.add(bl);
	}
	
	public boolean onRiver() {
		if(!river.contains(blptDst)) {
			return false;
		}
		if(!river.contains(brptDst)) {
			return false;
		}
		if(!river.contains(tlptDst)) {
			return false;
		}
		if(!river.contains(trptDst)) {
			return false;
		}
		return true;
	}
	
}
