package siver.agents.boat.actions;

import siver.agents.boat.BoatAgent;
import siver.agents.boat.CoxAgent;
import siver.agents.boat.CoxLocation;
import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class Spin extends Action {

	public Spin(CoxAgent cox) {
		super(cox);
	}
	
	@Override
	public void execute() {
		CoxLocation location = cox.getLocation();
		BoatAgent boat = cox.getBoat();
		
		boat.setSpeed(0);
		cox.setTickDistanceRemaining(0);
		double min_distance = Double.MAX_VALUE;
		LaneNode spin_target = null;
		Lane spin_to = null;
		if(location.headingUpstream()) {
			spin_to = boat.getRiver().getDownstream();
		} else {
			spin_to = boat.getRiver().getUpstream();
		}
		
		for(LaneNode node : spin_to.getNet().getNodes()) {
			if(min_distance > node.distance(location.getDestinationNode())) {
				spin_target = node;
				min_distance = node.distance(location.getDestinationNode());
			}
		}
		location.toggleUpstream();
		boat.steerToward(spin_target.getLocation());
		boat.move(min_distance);
		
		LaneEdge<LaneNode> new_edge = spin_to.getNextEdge(spin_target, location.headingUpstream());
		
		location.updateEdge(new_edge);
		boat.steerToward(new_edge.getNextNode(location.headingUpstream()).getLocation());
		
		boat.setSpeed(2);
	}

}
