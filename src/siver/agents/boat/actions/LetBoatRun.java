package siver.agents.boat.actions;

import siver.agents.boat.CoxAgent;

public class LetBoatRun extends Action {
	
	public LetBoatRun(CoxAgent cox) {
		super(cox);
	}
	
	@Override
	public void execute() {
		if(cox.canReachNextNode()) {
			cox.getBoat().move(cox.getLocation().getTillEdgeEnd());
			cox.getLocation().moveToEdgeEnd();
			cox.reactTo(cox.getLocation().getEdge().getNextNode(cox.getLocation().headingUpstream()));
		} else {
			
		}
	}
	
}
