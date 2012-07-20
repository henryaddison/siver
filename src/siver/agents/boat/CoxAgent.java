package siver.agents.boat;

import repast.simphony.engine.schedule.ScheduledMethod;
import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;
import siver.agents.boat.actions.*;

public class CoxAgent {
	//The boat the cox is controlling.
	protected BoatAgent boat;
	//distance that can be travelled this tick
	private double tick_distance_remaining;
	
	//how fast the boat would like to be travelling
	private int desired_gear;
	
	private Action action;
	protected CoxLocation location;
	
	public CoxAgent() {
	}
	
	public void launch(BoatAgent boat, Lane launchLane, int desGear) {
		//save reference to boat launched in
		this.boat = boat;
		
		this.desired_gear = desGear;
		
		//place the boat at the location of the first node of the lane
		LaneNode launchNode = launchLane.getStartNode();
		boat.launch(this, launchNode.getLocation());
		
		//and point the boat in the correct direction
		LaneEdge<LaneNode> launchEdge = launchLane.getNextEdge(launchNode, false);
		location = new CoxLocation(this, launchEdge, false);
	}
	
	//BEHAVIOUR
	@ScheduledMethod(start = 1, interval = 1, shuffle=true, priority=10)
	public void step() {
		if(action == null) {
			chooseAction();
		}
		action.execute();
		tick_distance_remaining = boat.getSpeed();
		boat.run();
	}
	
	public void chooseAction() {
		if(backAtBoatHouse()) {
			action = new Land(this);
		}
		else if(atRiversEnd()) {
			action = new Spin(this);
		}
		else if(belowDesiredSpeed()) {
			action = new SpeedUp(this);
		}
		else if(true) {
			action = new LetBoatRun(this);
		}
	}
	
	/*
	 * PREDICATES
	 */	
	
	private boolean atRiversEnd() { 
		LaneNode node = location.getDestinationNode();
		LaneEdge<LaneNode> next_edge = node.getLane().getNextEdge(node, location.headingUpstream());
		return next_edge == null;
	}
	
	private boolean backAtBoatHouse() {
		return location.getDestinationNode().equals(location.getLane().getStartNode());
	}
	
	public boolean belowDesiredSpeed() {
		return boat.getGear() < desired_gear;
	}
	
	/*
	 * HELPERS
	 */
	public Action getAction() {
		return action;
	}
	
	public void clearAction() {
		action = null;
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
