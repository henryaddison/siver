package siver.agents.boat;
// CoxAgent will use the boat it is attached to in order to decide how to alter it's
import java.awt.geom.Point2D;
import java.util.Iterator;


import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.graph.RepastEdge;
import repast.simphony.util.ContextUtils;
import siver.context.SiverContextCreator;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class CoxAgent {
	/**
	 * The boat the cox is controlling.
	 */
	private BoatAgent boat;
	
	/**
	 * Progress made by the cox along the current edge
	 */
	private double progress;
	/**
	 * Indicates whether the cox is trying to head upstream (towards boathouse) or not.
	 */
	private boolean upstream;
	
	public CoxAgent(BoatAgent boat) {
		this.boat = boat;
		// initially the cox wants to head downstream
		this.upstream = false;
		this.progress = 0;
	}
	
	
	public void step() {
		if(true) {
//			travel(boat.getSpeed());
			return;
		}
	}
	
//	private void travel(double distance_to_cover) {
//		double distance_till_next_node = current_edge.getWeight() - progress;
//		if(distance_to_cover < distance_till_next_node) {
//			progress += distance_to_cover;
//			boat.move(distance_to_cover);
//		} else {
//			boat.move(distance_till_next_node);
//			setNextEdge();
//			travel(distance_to_cover-distance_till_next_node);
//		}
//	}
//	
//	/*
//	 * PREDICATES
//	 */
//	
//	
//	/*
//	 * ACTIONS
//	 */
//	private void setNextEdge() {
//		if(!upstream) {
//			Iterator<RepastEdge<LaneNode>> i = SiverContextCreator.getMiddleLane().getOutEdges(current_edge.getTarget()).iterator();
//			if(!i.hasNext()) {
//				upstream = !upstream;
//				setNextEdge();
//			} else {
//				current_edge = (LaneEdge<LaneNode>) i.next();
//				progress = 0;
//				aimToward(current_edge.getTarget().getLocation());
//			}
//			return;
//		}
//		if(true){
//			Iterator<RepastEdge<LaneNode>> i = SiverContextCreator.getMiddleLane().getInEdges(current_edge.getSource()).iterator();
//			if(!i.hasNext()) {
//				upstream = !upstream;
//				setNextEdge();
//			} else {
//				current_edge = (LaneEdge<LaneNode>) i.next();
//				progress = 0;
//				aimToward(current_edge.getSource().getLocation());
//			}
//			return;
//		}
//	}
//	
//	
//	private void aimToward(Point2D.Double pt) {
//		Context<Object> context = ContextUtils.getContext(this);
//		ContinuousSpace<Object> space = (ContinuousSpace) context.getProjection("Continuous Space");
//		
//		NdPoint myPoint  = boat.getLocation();
//		NdPoint otherPoint = new NdPoint(pt.getX(), pt.getY());
//		double angle = SpatialMath.calcAngleFor2DMovement(space, myPoint, otherPoint);
//		boat.setAngle(angle);
//	}
	
	/*
	 * HELPERS
	 */
	
}
