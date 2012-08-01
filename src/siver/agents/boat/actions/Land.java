package siver.agents.boat.actions;

import siver.boat.*;
import siver.context.SiverContextCreator;

public class Land extends SingleTickAction {
	
	public Land(Cox cox) {
		super(cox);
	}
	
	@Override
	public void doExecute() {
		cox.getNavigator().getEdge().removeCox(cox);
		SiverContextCreator.getContext().remove(cox);
		SiverContextCreator.getContext().remove(boat);
	}

}
