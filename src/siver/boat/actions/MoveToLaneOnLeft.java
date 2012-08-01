package siver.boat.actions;

import siver.boat.Cox;
import siver.river.River.NoLaneFound;


public class MoveToLaneOnLeft extends ChangeLane {
	public MoveToLaneOnLeft(Cox cox) {
		super(cox);
	}
	
	@Override
	protected void directionSpecificSetup() throws NoLaneFound {
		setTargetLane(boat.getRiver().getLaneToLeftOf(startLane, location.headingUpstream()));
	}
}
