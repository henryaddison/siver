package siver.agents.boat.actions;

import siver.agents.boat.CoxAgent;

public class LetBoatRun extends Action {
	
	public LetBoatRun(CoxAgent cox) {
		super(cox);
	}
	
	@Override
	public void execute() {
		double distance_till_next_node = location.getTillEdgeEnd();
		double distance_can_travel = cox.getTickDistanceRemaining();
		if(distance_can_travel >= distance_till_next_node) {
			boat.move(distance_till_next_node);
			location.moveToEdgeEnd();
			cox.setTickDistanceRemaining(distance_can_travel - distance_till_next_node);
			cox.makeDecision();
		} else {
			boat.move(distance_can_travel);
			location.moveAlongEdge(distance_can_travel);
			cox.setTickDistanceRemaining(0);
		}
	}
	
}
