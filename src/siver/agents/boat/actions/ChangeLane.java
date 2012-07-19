package siver.agents.boat.actions;

import siver.agents.boat.CoxAgent;
import siver.river.River.NoLaneFound;
import siver.river.lane.Lane;
import siver.river.lane.LaneChangeEdge;
import siver.river.lane.LaneNode;

public abstract class ChangeLane extends Action {
	final protected static int nodes_ahead_to_aim_for = 6;
	protected Lane targetLane;
	protected Lane startLane;
	
	
	public ChangeLane(CoxAgent cox) {
		super(cox);
	}
	
	protected void setTargetLane(Lane tl) {
		targetLane = tl;
	}
	
	protected void setStartLane(Lane sl) {
		startLane = sl;
	}
	
	public Lane getTargetLane() {
		return targetLane;
	}
	
	public Lane getStartLane() {
		return startLane;
	}
	
	@Override
	public void execute() {
		setStartLane(location.getLane());
		try {
			directionSpecificSetup();
		} catch (NoLaneFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		LaneNode startingNode = new LaneNode(boat.getLocation(), targetLane);
		
		LaneNode nearestNode = targetLane.nodeNearest(boat.getLocation());
		LaneNode destinationNode = targetLane.getNthNodeAhead(nearestNode, location.headingUpstream(), nodes_ahead_to_aim_for);
		
		
		LaneNode source = startingNode;
		LaneNode target = destinationNode;
		
		if(location.headingUpstream()) {
			source = destinationNode;
			target = startingNode;
		}
		
		LaneChangeEdge<LaneNode> edge = new LaneChangeEdge<LaneNode>(source, target);
		targetLane.getContext().add(startingNode);
		
		targetLane.getNet().addEdge(edge);
		location.updateEdge(edge);
		boat.steerToward(destinationNode.getLocation());
	}
	
	protected abstract void directionSpecificSetup() throws NoLaneFound;

}
