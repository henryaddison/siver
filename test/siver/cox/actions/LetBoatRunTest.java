package siver.cox.actions;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import siver.boat.*;
import siver.cox.Cox;
import siver.cox.actions.LetBoatRun;


public class LetBoatRunTest extends ActionTest {
	@Override
	protected String className() {
		return LetBoatRun.class.getName();
	}
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	@Test
	public void testExecute() {
		executeWithMocks();
		
	}
	
	public void testExecuteCanReachNextNode() {
		double exp_distance_travelled = 3.5;
		
		expect(mockLocation.getTillEdgeEnd()).andReturn(exp_distance_travelled).once();
		
		expect(mockBoat.getTickDistanceRemaining()).andReturn(5.0).once();
		
		mockBoat.move(exp_distance_travelled);
		expectLastCall().once();
		
		mockLocation.moveToEdgeEnd();
		expectLastCall().once();
		
		mockBoat.setTickDistanceRemaining(1.5);
		expectLastCall().once();
		
		replay(mockBoat);
		replay(mockCox);
		replay(mockLocation);
		
		action.execute();
		
		verify(mockBoat);
		verify(mockCox);
		verify(mockLocation);
	}
	
	
	public void testExecuteCannotReachNextNode() {	
		setUpRunAlongEdgeExpectations(mockCox, mockBoat, mockLocation);
		replay(mockBoat);
		replay(mockCox);
		replay(mockLocation);
		
		action.execute();
		
		verify(mockBoat);
		verify(mockCox);
		verify(mockLocation);
	}
	
	public static void setUpRunAlongEdgeExpectations(Cox mCox, Boat mBoat, BoatNavigation mLoc) {
		expect(mCox.getNavigator()).andStubReturn(mLoc);
		expect(mCox.getBoat()).andStubReturn(mBoat);
		
		expect(mLoc.getTillEdgeEnd()).andReturn(9.0).once();
		expect(mBoat.getTickDistanceRemaining()).andReturn(5.0).once();
		
		mBoat.move(5.0);
		expectLastCall().once();
		
		mLoc.moveAlongEdge(5.0);
		expectLastCall().once();
		
		mBoat.setTickDistanceRemaining(0);
		expectLastCall().once();
	}

	

}
