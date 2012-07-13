package siver.agents.boat.actions;

import static org.easymock.EasyMock.createMock;
import siver.agents.boat.BoatAgent;
import siver.agents.boat.CoxAgent;

public abstract class ActionTest {
	protected BoatAgent mockBoat;
	protected CoxAgent mockCox;
	protected Action action;
	
	protected void setUpMocks() {
		mockBoat = createMock(BoatAgent.class);
		mockCox = createMock(CoxAgent.class);
	}
	
}
