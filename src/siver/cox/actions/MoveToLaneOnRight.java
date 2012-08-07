package siver.cox.actions;

import siver.cox.Cox;
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