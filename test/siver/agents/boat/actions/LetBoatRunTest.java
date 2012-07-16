package siver.agents.boat.actions;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import siver.river.lane.*;


public class LetBoatRunTest extends ActionTest {

	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		setUpMocks();
		action = new LetBoatRun(mockCox);
		reset(mockCox);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testExecuteCanReachNextNode() {
		double exp_distance_travelled = 3.5;
		
		expect(mockLocation.getTillEdgeEnd()).andReturn(exp_distance_travelled).once();
		
		expect(mockCox.getTickDistanceRemaining()).andReturn(5.0).once();
		
		mockBoat.move(exp_distance_travelled);
		expectLastCall().once();
		
		mockLocation.moveToEdgeEnd();
		expectLastCall().once();
		
		mockCox.setTickDistanceRemaining(1.5);
		expectLastCall().once();
		
		LaneNode edge_start = new LaneNode(10,30,null);
		LaneNode edge_end = new LaneNode(30,30,null);
		LaneEdge<LaneNode> edge = new LaneEdge<LaneNode>(edge_start, edge_end); 
		

		
		mockCox.reactToLocation();
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
		expect(mockLocation.getTillEdgeEnd()).andReturn(9.0).once();
		expect(mockCox.getTickDistanceRemaining()).andReturn(5.0).once();
		
		mockBoat.move(5.0);
		expectLastCall().once();
		
		mockLocation.moveAlongEdge(5.0);
		expectLastCall().once();
		
		mockCox.setTickDistanceRemaining(0);
		expectLastCall().once();
		
		replay(mockBoat);
		replay(mockCox);
		replay(mockLocation);
		
		action.execute();
		
		verify(mockBoat);
		verify(mockCox);
		verify(mockLocation);
	}

}
