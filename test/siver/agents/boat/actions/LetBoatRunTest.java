package siver.agents.boat.actions;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import siver.agents.boat.CoxLocation;
import siver.river.lane.*;


public class LetBoatRunTest extends ActionTest {

	private CoxLocation mockLocation;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		setUpMocks();
		mockLocation = createMock(CoxLocation.class);
		action = new LetBoatRun(mockCox);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testExecuteCanReachNextNode() {
		expect(mockCox.canReachNextNode()).andReturn(true).once();
		expect(mockCox.getBoat()).andReturn(mockBoat).once();
		expect(mockCox.getLocation()).andReturn(mockLocation).andStubReturn(mockLocation);
		expect(mockLocation.getTillEdgeEnd()).andReturn(20.0).once();
		mockBoat.move(20.0);
		expectLastCall().once();
		mockLocation.moveToEdgeEnd();
		expectLastCall().once();
		
		LaneNode edge_start = new LaneNode(10,30,null);
		LaneNode edge_end = new LaneNode(30,30,null);
		LaneEdge<LaneNode> edge = new LaneEdge<LaneNode>(edge_start, edge_end); 
		
		expect(mockLocation.getEdge()).andReturn(edge).once();
		expect(mockLocation.headingUpstream()).andReturn(false).once();
		
		mockCox.reactTo(edge_end);
		expectLastCall().once();
		
		replay(mockBoat);
		replay(mockCox);
		replay(mockLocation);
		
		action.execute();
		
		verify(mockBoat);
		verify(mockCox);
		verify(mockLocation);
	}
	
	@Test
	public void testExecuteCannotReachNextNode() {
		expect(mockCox.canReachNextNode()).andReturn(false).once();
		
		replay(mockBoat);
		replay(mockCox);
		
		action.execute();
		
		verify(mockBoat);
		verify(mockCox);
	}

}
