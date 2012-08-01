package siver.agents.boat.actions;

import siver.agents.boat.Cox;
import siver.river.River.NoLaneFound;

public class MoveToLaneOnRight extends ChangeLane {
	
	public MoveToLaneOnRight(Cox cox) {
		super(cox);
	}

	@Override
	protected void directionSpecificSetup() throws NoLaneFound {
		setTargetLane(boat.getRiver().getLaneToRightOf(startLane, location.headingUpstream()));
	}

}
