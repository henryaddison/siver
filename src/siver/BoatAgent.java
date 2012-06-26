package siver;


import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
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
	/**
	 * The River object that the boat is considered to be on
	 */
	private River river;
	private double angle;
	private CoxAgent cox;
	private BoatCorner tl,tr,br,bl;
	
	Point2D.Double blptDst = new Point2D.Double();
	Point2D.Double brptDst = new Point2D.Double();
	Point2D.Double trptDst = new Point2D.Double();
	Point2D.Double tlptDst = new Point2D.Double();
	
	public BoatAgent(River river) {
		
		this.river = river;
		// and points straight up
		this.angle = 0;		
	}
	
	public void launch(NdPoint pt) {
		Context<Object> context = ContextUtils.getContext(this);
		Grid<Object> grid = (Grid) context.getProjection("Simple Grid");
		ContinuousSpace<Object> space = (ContinuousSpace) context.getProjection("Continuous Space");
		
		this.cox = new CoxAgent(this);
		context.add(this.cox);
		
		space.moveTo(this, pt.getX(), pt.getY());
		grid.moveTo(this, (int)pt.getX(), (int)pt.getY());
		tl = new BoatCorner();
		tr = new BoatCorner();
		br = new BoatCorner();
		bl = new BoatCorner();
		context.add(tl);
		context.add(tr);
		context.add(bl);
		context.add(br);
	}
	
	@ScheduledMethod(start = 1, interval = 1, shuffle=true, priority=1)
	public void step() {
		Context<Object> context = ContextUtils.getContext(this);
		Grid<Object> grid = (Grid) context.getProjection("Simple Grid");
		ContinuousSpace<Object> space = (ContinuousSpace) context.getProjection("Continuous Space");
		
		space.moveByVector(this, 1, angle, 0);
		grid.moveTo(this, (int)getLocation().getX(), (int)getLocation().getY());
		
		
		AffineTransform at = new AffineTransform();
		at.translate(getLocation().getX(), getLocation().getY());
		at.rotate(angle);
		
		at.transform(new Point2D.Double(-8.5,-3.5), blptDst);
		space.moveTo(bl, blptDst.getX(), blptDst.getY());
		
		at.transform(new Point2D.Double(8.5,-3.5), brptDst);
		space.moveTo(br, brptDst.getX(), brptDst.getY());
		
		
		at.transform(new Point2D.Double(-8.5,3.5), tlptDst);
		space.moveTo(tl, tlptDst.getX(), tlptDst.getY());
		
		at.transform(new Point2D.Double(8.5,3.5), trptDst);
		space.moveTo(tr, trptDst.getX(), trptDst.getY());
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public NdPoint getLocation() {
		Context<Object> context = ContextUtils.getContext(this);
		ContinuousSpace<Object> space = (ContinuousSpace) context.getProjection("Continuous Space");
		
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
