package siver.boat.actions;

import static org.easymock.EasyMock.*;

import java.lang.reflect.Constructor;

import org.junit.Before;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.boat.Cox;
import siver.boat.actions.Action;
import siver.boat.actions.SingleTickAction;

public abstract class ActionTest {
	protected Boat mockBoat;
	protected Cox mockCox;
	protected Action action;
	protected BoatNavigation mockLocation;
	
	protected void setUpMocks() {
		mockBoat = createMock(Boat.class);
		mockCox = createMock(Cox.class);
		mockLocation = createMock(BoatNavigation.class);
		
		expect(mockCox.getNavigator()).andStubReturn(mockLocation);
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
		Constructor con = cl.getConstructor(Cox.class);
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
		
		
		expect(mockCox.getNavigator()).andStubReturn(mockLocation);
		expect(mockCox.getBoat()).andStubReturn(mockBoat);
	}
	
}
