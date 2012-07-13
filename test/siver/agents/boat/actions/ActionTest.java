package siver.agents.boat.actions;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import siver.agents.boat.BoatAgent;
import siver.agents.boat.CoxAgent;
import siver.agents.boat.CoxLocation;

public abstract class ActionTest {
	protected BoatAgent mockBoat;
	protected CoxAgent mockCox;
	protected Action action;
	protected CoxLocation mockLocation;
	
	protected void setUpMocks() {
		mockBoat = createMock(BoatAgent.class);
		mockCox = createMock(CoxAgent.class);
		mockLocation = createMock(CoxLocation.class);
		
		expect(mockCox.getLocation()).andStubReturn(mockLocation);
		expect(mockCox.getBoat()).andStubReturn(mockBoat);
	}
	
}
