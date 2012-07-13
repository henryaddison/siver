package siver.agents.boat;

import repast.simphony.engine.schedule.ScheduledMethod;
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
	
	//Temporal behaviour method - how to react as time changes, i.e. at each tick.
	@ScheduledMethod(start = 1, interval = 1, shuffle=true, priority=10)
	public void step() {
		tick_distance_remaining = boat.getSpeed();
		if(true) {
			action = new LetBoatRun(this);
		}
		action.execute();
	}
	
	//Spatial behaviour method - how to react as cox's position changes.
	public void reactTo(LaneNode node) {
		LaneEdge<LaneNode> next_edge = node.getLane().getNextEdge(node, upstream());
		if(atRiversEnd(next_edge)) {
			spin();
			return;
		}
		if(backAtBoatHouse(node)) {
			action = new Land(this);
			action.execute();
			return;
		}
		if(true) {
			steer(next_edge);
			return;
		}
	}
	
	/*
	 * ACTIONS
	 */
	private void steer(LaneEdge<LaneNode> next_edge) {
		aimAlong(next_edge);
		action = new LetBoatRun(this);
		action.execute();
	}
	
	private void spin() {
		boat.setSpeed(0);
		tick_distance_remaining = 0;
		double min_distance = Double.MAX_VALUE;
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
		boat.steerToward(spin_target.getLocation());
		boat.move(min_distance);
		
		reactTo(spin_target);
		boat.setSpeed(2);
	}
	
	private void aimAlong(LaneEdge<LaneNode> edge) {
		location.updateEdge(edge);
		boat.steerToward(edge.getNextNode(upstream()).getLocation());
	}
	
	/*
	 * PREDICATES
	 */	
	
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
	 * HELPERS
	 */
	public Action getAction() {
		return action;
	}
	
	public BoatAgent getBoat() {
		return boat;
	}
	
	public CoxLocation getLocation() {
		return location;
	}
	
	public double getTickDistanceRemaining() {
		return tick_distance_remaining;
	}
	
	public void setTickDistanceRemaining(double value) {
		tick_distance_remaining = value;
	}
	
}
