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
		LaneNode steer_from = location.getNode();
		Lane lane = steer_from.getLane();
		LaneEdge<LaneNode> next_edge =lane.getNextEdge(steer_from, location.headingUpstream());
		
		location.updateEdge(next_edge);
		boat.steerToward(location.getDestinationNode().getLocation());
		
		new LetBoatRun(cox).execute();
	}

}
