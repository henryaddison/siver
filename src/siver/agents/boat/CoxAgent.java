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
	
	//how fast the boat would like to be travelling
	private double desired_speed;
	
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
		boat.steerToward(location.getDestinationNode().getLocation());
		
		desired_speed = 4;
	}
	
	//BEHAVIOUR
	
	//Temporal behaviour method - how to react as time changes, i.e. at each tick.
	@ScheduledMethod(start = 1, interval = 1, shuffle=true, priority=10)
	public void step() {
		tick_distance_remaining = boat.getSpeed();
		makeDecision();
	}
	
	public void makeDecision() {
		if(backAtBoatHouse()) {
			action = new Land(this);
		}
		else if(atRiversEnd()) {
			action = new Spin(this);
		}
		else if(onNode()) {
			action = new Steer(this);
		}
		else if(belowDesiredSpeed()) {
			action = new SpeedUp(this);
		}
		else if(true) {
			action = new LetBoatRun(this);
		}
		action.execute();
	}
	
	/*
	 * PREDICATES
	 */	
	
	private boolean onNode() {
		return location.getNode() != null;
	}
	
	private boolean atRiversEnd() {
		if(!onNode()) return false; 
		LaneNode node = location.getNode();
		LaneEdge<LaneNode> next_edge = node.getLane().getNextEdge(node, upstream());
		return !upstream() && next_edge == null;
	}
	
	private boolean backAtBoatHouse() {
		if(!onNode()) return false;
		return upstream() && location.getNode().equals(location.getLane().getStartNode());
	}
	
	private boolean upstream() {
		return location.headingUpstream();
	}
	
	public boolean belowDesiredSpeed() {
		return boat.getSpeed() < desired_speed;
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
