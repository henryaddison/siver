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
	}
	
	protected abstract String className();
	
	protected void preNewActionSetup() {
		
	}
	
	protected void executeWithMocks() {
		replay(mockLocation);
		replay(mockBoat);
		replay(mockCox);
		action.execute();
		verify(mockCox);
		verify(mockBoat);
		verify(mockLocation);
	}
	
	@Before
	public void setUp() throws Exception {
		setUpMocks();
		
		preNewActionSetup();
		
		replay(mockCox);
		replay(mockBoat);
		replay(mockLocation);
		Class cl = Class.forName(className());
		Constructor con = cl.getConstructor(CoxAgent.class);
		action = (Action) con.newInstance(mockCox);
		verify(mockCox);
		verify(mockBoat);
		verify(mockLocation);
		
		reset(mockCox);
		reset(mockBoat);
		reset(mockLocation);
		
		if(action instanceof SingleTickAction) {
			mockCox.clearAction();
			expectLastCall().once();
		}
		
		
		expect(mockCox.getLocation()).andStubReturn(mockLocation);
		expect(mockCox.getBoat()).andStubReturn(mockBoat);
	}
	
}
