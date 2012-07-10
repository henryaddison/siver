package siver.agents.boat;


import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.util.ContextUtils;
import siver.river.River;

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
	
	//the current speed, orientation
	private double angle;
	private double speed;
	
	//keep a reference of the space the boat is in for easier movement
	private ContinuousSpace<Object> space;
	private Context<Object> context;
	
	//some debugging variables for checking collision detection 
	private BoatCorner tl,tr,br,bl;
	Point2D.Double blptDst = new Point2D.Double();
	Point2D.Double brptDst = new Point2D.Double();
	Point2D.Double trptDst = new Point2D.Double();
	Point2D.Double tlptDst = new Point2D.Double();
	
	public BoatAgent(River river, Context<Object> context, ContinuousSpace<Object> space) {
		this.river = river;
		this.space = space;
		this.context = context;
	}
	
	public void launch(CoxAgent cox, Point2D.Double pt) {
		//initially the boat points straight up and is going at speed 10
		this.angle = 0;
		this.speed = 5;
		this.cox = cox;
		
		space.moveTo(this, pt.getX(), pt.getY());
		
		//now add the cox and the 4 corners for collision detection
		setupCorners();
	}
	
	//MOVEMENT
	
	public void move(double dist) {
		if(dist > 0) {
			space.moveByVector(this, dist, angle, 0);
			
			AffineTransform at = new AffineTransform();
			at.translate(getLocation().getX(), getLocation().getY());
			at.rotate(angle);
			
			at.transform(new Point2D.Double(-8.5,-3.5), blptDst);
			at.transform(new Point2D.Double(8.5,-3.5), brptDst);
			at.transform(new Point2D.Double(-8.5,3.5), tlptDst);
			at.transform(new Point2D.Double(8.5,3.5), trptDst);
		}
	}
	
	//GETTERS AND SETTERS
	public double getAngle() {
		return angle;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double new_speed) {
		this.speed = new_speed;
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
	
	//COLLISION DETECTION
	private void setupCorners() {
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
