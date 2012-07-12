package siver.agents.boat;
// CoxAgent will use the boat it is attached to in order to decide how to alter it's
import java.awt.geom.Point2D;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.NdPoint;
import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;
import siver.agents.boat.actions.*;

public class CoxAgent {
	//The boat the cox is controlling.
	private BoatAgent boat;
	
	//distance that can be travelled this tick
	private double tick_distance_remaining;
	
	private Action action;
	
	private CoxLocation location;
	
	public CoxAgent() {
	}
	
	public void launch(BoatAgent boat, Lane launchLane) {
		//save reference to boat launched in
		this.boat = boat;
		
		//place the boat at the location of the first node of the lane
		LaneNode launchNode = launchLane.getStartNode();
		boat.launch(this, launchNode.getLocation());
		
		//and point the boat in the correct direction
		LaneEdge<LaneNode> launchEdge = launchLane.getNextEdge(launchNode, false);
		location = new CoxLocation(launchEdge, false);
		aimAlong(launchEdge);
	}
	
	//BEHAVIOUR
	
	@ScheduledMethod(start = 1, interval = 1, shuffle=true, priority=10)
	public void step() {
		tick_distance_remaining = boat.getSpeed();
		if(true) {
			letBoatRun();
			action = new LetBoatRun();
		}
		
		action.execute();
	}
	
	/*
	 * PREDICATES
	 */
	
	private boolean canReachNextNode() {
		return tick_distance_remaining >= location.getTillEdgeEnd(); 
	}
	
	private boolean atRiversEnd(LaneEdge<LaneNode> next_edge) {
		return !upstream() && next_edge == null;
	}
	
	private boolean backAtBoatHouse(LaneNode node) {
		return upstream() && node.equals(location.getLane().getStartNode());
	}
	
	private boolean upstream() {
		return location.headingUpstream();
	}
	
	/*
	 * ACTIONS
	 */
	
	private void reactTo(LaneNode node) {
		LaneEdge<LaneNode> next_edge = node.getLane().getNextEdge(node, upstream());
		if(atRiversEnd(next_edge)) {
			spin();
			return;
		}
		if(backAtBoatHouse(node)) {
			boat.land();
			return;
		}
		if(true) {
			aimAlong(next_edge);
			letBoatRun();
			return;
		}
	}
	
	private void spin() {
		boat.setSpeed(0);
		tick_distance_remaining = 0;
		double min_distance = Integer.MAX_VALUE;
		LaneNode spin_target = null;
		Lane spin_to = null;
		if(upstream()) {
			spin_to = boat.getRiver().getDownstream();
		} else {
			spin_to = boat.getRiver().getUpstream();
		}
		
		for(LaneNode node : spin_to.getNet().getNodes()) {
			if(min_distance > node.distance(location.getEdge().getNextNode(upstream()))) {
				spin_target = node;
				min_distance = node.distance(location.getEdge().getNextNode(upstream()));
			}
		}
		location.toggleUpstream();
		steerToward(spin_target.getLocation());
		boat.move(min_distance);
		
		reactTo(spin_target);
		boat.setSpeed(2);
	}
	
	private void letBoatRun() {
		if(canReachNextNode()) {
			boat.move(location.getTillEdgeEnd());
			location.moveToEdgeEnd();
			reactTo(location.getEdge().getNextNode(upstream()));
		} else {
			boat.move(tick_distance_remaining);
			location.moveAlongEdge(tick_distance_remaining);
		}
	}
	
	private void aimAlong(LaneEdge<LaneNode> edge) {
		location.updateEdge(edge);
		steerToward(edge.getNextNode(upstream()).getLocation());
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
	public Action getAction() {
		return action;
	}
}
