package siver.agents.boat.actions;

import siver.agents.boat.CoxAgent;
import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class Steer extends Action {
	
	public Steer(CoxAgent cox) {
		super(cox);
	}
	@Override
	public void execute() {
		LaneNode steer_from = cox.getLocation().getNode();
		Lane lane = steer_from.getLane();
		LaneEdge<LaneNode> next_edge =lane.getNextEdge(steer_from, cox.getLocation().headingUpstream());
		
		cox.getLocation().updateEdge(next_edge);
		cox.getBoat().steerToward(cox.getLocation().getDestinationNode().getLocation());
	}

}
