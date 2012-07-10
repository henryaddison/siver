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
	private double progress;
	private double distance_covered_this_tick;
	
	// The part of a the lane the boat is currently travelling on
	private LaneEdge<LaneNode> current_edge;
	
	private Lane lane;
	
	public CoxAgent(BoatAgent boat) {
		this.boat = boat;
	}
	
	
	public void launch(Lane launchLane) throws UnstartedLaneException {
		// initially the cox wants to head downstream in the lane launched in
		this.upstream = false;
		this.lane = launchLane;
		
		//place the boat at the location of the first node of the lane
		LaneNode launchNode = lane.getStartNode();
		boat.launch(this, launchNode.getLocation());
		//and have the cox react to this node (i.e. point the boat in the correct direction)
		reactTo(launchNode);
	}
	
	@ScheduledMethod(start = 1, interval = 1, shuffle=true, priority=10)
	public void step() {
		distance_covered_this_tick = 0;
		if(true) {
			travel(boat.getSpeed());
			return;
		}
	}
	
	private void travel(double distance_to_cover) {
		if(distance_covered_this_tick >= boat.getSpeed()) {
			return;
		}
		double distance_till_next_node = current_edge.getWeight() - progress;
		if(distance_to_cover < distance_till_next_node) {
			progress += distance_to_cover;
			boat.move(distance_to_cover);
		} else {
			progress += distance_till_next_node;
			boat.move(distance_till_next_node);
			reactTo(current_edge.getNextNode(upstream));
			travel(distance_to_cover-distance_till_next_node);
		}
	}
	
	/*
	 * PREDICATES
	 */
	
	
	/*
	 * ACTIONS
	 */
	
	private void reactTo(LaneNode node) {
		LaneEdge<LaneNode> next_edge = lane.getNextEdge(node, upstream);
		if(next_edge == null) {
			boat.setSpeed(0);
		} else {
			aimAlong(next_edge);
		}
	}
	
	private void aimAlong(LaneEdge<LaneNode> edge) {
		LaneNode next_node = edge.getNextNode(upstream);
		aimToward(next_node.getLocation());
		this.current_edge = edge;
		this.progress = 0;
	}
	
	
	private void aimToward(Point2D.Double pt) {
		NdPoint myPoint  = boat.getLocation();
		NdPoint otherPoint = new NdPoint(pt.getX(), pt.getY());
		double angle = SpatialMath.calcAngleFor2DMovement(boat.getSpace(), myPoint, otherPoint);
		boat.setAngle(angle);
	}
	
	/*
	 * HELPERS
	 */
	
}
