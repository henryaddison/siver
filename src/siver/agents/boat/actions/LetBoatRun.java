package siver.agents.boat.actions;

import siver.agents.boat.CoxAgent;

public class LetBoatRun extends Action {
	
	public LetBoatRun(CoxAgent cox) {
		super(cox);
	}
	
	@Override
	public void execute() {
		double distance_till_next_node = cox.getLocation().getTillEdgeEnd();
		double distance_can_travel = cox.getTickDistanceRemaining();
		if(distance_can_travel >= distance_till_next_node) {
			cox.getBoat().move(distance_till_next_node);
			cox.getLocation().moveToEdgeEnd();
			cox.setTickDistanceRemaining(distance_can_travel - distance_till_next_node);
			cox.reactToLocation();
		} else {
			cox.getBoat().move(distance_can_travel);
			cox.getLocation().moveAlongEdge(distance_can_travel);
			cox.setTickDistanceRemaining(0);
		}
	}
	
}
