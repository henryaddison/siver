package siver.agents.boat.actions;

import siver.agents.boat.CoxAgent;
import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class Spin extends Action {

	public Spin(CoxAgent cox) {
		super(cox);
	}
	
	@Override
	public void execute() {		
		boat.setGear(0);
		cox.setTickDistanceRemaining(0);
		
		LaneNode spin_target = null;
		Lane spin_to = null;
		if(location.headingUpstream()) {
			spin_to = boat.getRiver().getDownstream();
		} else {
			spin_to = boat.getRiver().getUpstream();
		}
		
		spin_target = spin_to.nodeNearest(boat.getLocation());
		
		location.toggleUpstream();
		boat.steerToward(spin_target.getLocation());
		boat.move(spin_target.distance(boat.getLocation()));
		
		LaneEdge<LaneNode> new_edge = spin_to.getNextEdge(spin_target, location.headingUpstream());
		location.updateEdge(new_edge);
		boat.steerToward(new_edge.getNextNode(location.headingUpstream()).getLocation());
	}

}
