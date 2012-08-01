package siver.river.lane;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.space.graph.Network;
import repast.simphony.space.graph.RepastEdge;
import siver.agents.boat.Cox;
import siver.agents.boat.BoatNavigation;
import siver.context.LaneContext;

public class LaneChangeEdgeTest extends EdgeTester {
	private LaneChangeEdge e;
	private LaneEdge sLEdge, dLEdge;
	private LaneContext laneContext;
	private Network<LaneNode> mockNet;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		sLEdge = createMock(LaneEdge.class);
		dLEdge = createMock(LaneEdge.class);
		Lane mockLane = createMock(Lane.class);
		mockNet = createMock(Network.class);
		laneContext = new LaneContext("Test");
		
		expect(mockLane.getNet()).andStubReturn(mockNet);
		expect(mockLane.getContext()).andStubReturn(laneContext);
		
		replay(mockLane);
		
		LaneNode s = new LaneNode(0,0,mockLane);
		LaneNode d = new LaneNode(3,4,mockLane);
		e = new LaneChangeEdge(s,d, sLEdge, dLEdge);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddCox() {
		Cox cox = new Cox();
		
		sLEdge.addCox(cox);
		expectLastCall().once();
		
		dLEdge.addCox(cox);
		expectLastCall().once();
		
		replay(sLEdge, dLEdge);
		
		e.addCox(cox);
		
		verify(sLEdge, dLEdge);
	}

	@Test
	public void testRemoveCox() {
		Cox cox = createMock(Cox.class);
		BoatNavigation mockLocation = createMock(BoatNavigation.class);
		
		expect(cox.getNavigator()).andStubReturn(mockLocation);
		expect(mockLocation.headingUpstream()).andReturn(false).once();
		
		mockNet.removeEdge((RepastEdge<LaneNode>) e);
		expectLastCall().once();
		
		sLEdge.removeCox(cox);
		expectLastCall().once();
		
		dLEdge.removeCox(cox);
		expectLastCall().once();
		
		replay(sLEdge, dLEdge, mockNet, cox, mockLocation);
		
		e.removeCox(cox);
		
		verify(sLEdge, dLEdge, mockLocation);
	}
	
	@Test
	public void testIsTemporary() {
		assertTrue(e.isTemporary());
	}

}
