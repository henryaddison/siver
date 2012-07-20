package siver.agents.boat.actions;

import static org.easymock.EasyMock.*;

import java.lang.reflect.Constructor;

import org.junit.Before;

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
		
		replay(mockCox);
	}
	
	protected abstract String className();
	
	protected void preNewActionSetup() {
		
	}
	
	@Before
	public void setUp() throws Exception {
		setUpMocks();
		
		preNewActionSetup();
		
		Class cl = Class.forName(className());
		Constructor con = cl.getConstructor(CoxAgent.class);
		action = (Action) con.newInstance(mockCox);
		verify(mockCox);
		reset(mockCox);
		
		mockCox.clearAction();
		expectLastCall().once();
	}
	
}
