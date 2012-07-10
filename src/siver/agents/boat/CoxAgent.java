package siver.agents.boat;
// CoxAgent will use the boat it is attached to in order to decide how to alter it's
import java.awt.geom.Point2D;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.NdPoint;
import siver.river.lane.Lane;
import siver.river.lane.Lane.UnstartedLaneException;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class CoxAgent {
	//The boat the cox is controlling.
	private BoatAgent boat;
	
	// Indicates whether the cox is trying to head upstream (towards boathouse) or not.
	private boolean upstream;
	
	//Progress made by the boat along the current edge
	private double edge_remaining;
	//distance that can be travelled this tick
	private double tick_distance_remaining;
	
	// The part of a the lane the boat is currently travelling on
	private LaneEdge<LaneNode> current_edge;
	
	private Lane lane;
	
	public CoxAgent() {
	}
	
	public void launch(BoatAgent boat, Lane launchLane) throws UnstartedLaneException {
		// initially the cox wants to head downstream in the lane launched in
		this.upstream = false;
		this.lane = launchLane;
		
		//save reference to boat launched in
		this.boat = boat;
		
		//place the boat at the location of the first node of the lane
		LaneNode launchNode = lane.getStartNode();
		boat.launch(this, launchNode.getLocation());
		
		//and have the cox react to this node (i.e. point the boat in the correct direction)
		reactTo(launchNode);
	}
	
	//BEHAVIOUR
	
	@ScheduledMethod(start = 1, interval = 1, shuffle=true, priority=10)
	public void step() {
		tick_distance_remaining = boat.getSpeed();
		if(true) {
			letBoatRun();
			return;
		}
	}
	
	/*
	 * PREDICATES
	 */
	
	private boolean canReachNextNode() {
		return tick_distance_remaining < edge_remaining; 
	}
	
	/*
	 * ACTIONS
	 */
	
	private void reactTo(LaneNode node) {
		LaneEdge<LaneNode> next_edge = lane.getNextEdge(node, upstream);
		if(next_edge == null) {
			boat.setSpeed(0);
		} else {
			aimAlong(next_edge);
			letBoatRun();
		}
	}
	
	private void letBoatRun() {
		if(canReachNextNode()) {
			boat.move(tick_distance_remaining);
			edge_remaining -= tick_distance_remaining;
		} else {
			boat.move(edge_remaining);
			edge_remaining = 0;
			reactTo(current_edge.getNextNode(upstream));
		}
	}
	
	private void aimAlong(LaneEdge<LaneNode> edge) {
		this.current_edge = edge;
		this.edge_remaining = current_edge.getWeight();
		LaneNode next_node = edge.getNextNode(upstream);
		steerToward(next_node.getLocation());
	}
	
	private void steerToward(Point2D.Double pt) {
		NdPoint myPoint  = boat.getLocation();
		NdPoint otherPoint = new NdPoint(pt.getX(), pt.getY());
		double angle = SpatialMath.calcAngleFor2DMovement(boat.getSpace(), myPoint, otherPoint);
		boat.setAngle(angle);
	}
	
	/*
	 * HELPERS
	 */
	
}
