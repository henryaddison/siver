package siver.agents.boat;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.space.continuous.ContinuousSpace;
import siver.agents.boat.actions.LetBoatRun;
import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class CoxAgentTest {
	
	private CoxAgent cox;
	private BoatAgent mockBoat;
	private Lane mockLane;
	private ContinuousSpace<Object> mockSpace;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mockBoat = createMock(BoatAgent.class);
		mockLane = createMock(Lane.class);
		mockSpace = createMock(ContinuousSpace.class);
		cox = new CoxAgent();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCoxAgent() {
		assertNotNull(cox);
		assertTrue(cox instanceof CoxAgent);
	}

	@Test
	public void testLaunch() {
		launchCox();
	}
	
	private void launchCox() {
		Point2D.Double expLoc = new Point2D.Double(10,30);
		LaneNode expNode = new LaneNode(expLoc, mockLane);
		LaneNode nextNode = new LaneNode(30,30, mockLane);
		expect(mockLane.getStartNode()).andStubReturn(expNode);
		mockBoat.launch(cox, expLoc);
		expectLastCall().once();
		expect(mockLane.getNextEdge(expNode, false)).andReturn(new LaneEdge<LaneNode>(expNode, nextNode)).once();
		mockBoat.steerToward(nextNode.getLocation());
		expectLastCall().once();
		
		replay(mockBoat);
		replay(mockLane);
		replay(mockSpace);
		cox.launch(mockBoat, mockLane, 8);
		verify(mockBoat);
		verify(mockLane);
		verify(mockSpace);
		
		assertEquals(nextNode, cox.getLocation().getDestinationNode());
		
		reset(mockLane);
		reset(mockSpace);
		reset(mockBoat);
	}

	@Test
	public void testStep() {
		launchCox();
		
		Point2D.Double expLoc = new Point2D.Double(10,30);
		LaneNode expNode = new LaneNode(expLoc, mockLane);
		LaneNode nextNode = new LaneNode(30,30, mockLane);
		LaneNode furtherNode = new LaneNode(50,30, mockLane);
		
		expect(mockBoat.getSpeed()).andReturn(5.0).times(1);
		expect(mockBoat.getGear()).andReturn(10).times(1);
		mockBoat.run();
		expectLastCall().once();
		
		expect(mockLane.getStartNode()).andStubReturn(expNode);
		expect(mockLane.getNextEdge(cox.getLocation().getDestinationNode(), false)).andReturn(new LaneEdge<LaneNode>(cox.getLocation().getDestinationNode(), furtherNode)).once();
		
		replay(mockLane);
		replay(mockBoat);
		cox.step();
		assertTrue(cox.getAction() instanceof LetBoatRun);
		verify(mockBoat);
		verify(mockLane);
	}
	
	@Test
	public void testGetAction() {
		assertNull(cox.getAction());
	}
	
	@Test
	public void testGetBoat() {
		launchCox();
		assertEquals(mockBoat, cox.getBoat());
	}
	
	@Test
	public void testGetLocation() {
		launchCox();
		CoxLocation cl = cox.getLocation();
		assertEquals(mockLane, cl.getLane());
		assertEquals(new Point2D.Double(10,30), cl.getEdge().getSource().getLocation());
		assertEquals(new Point2D.Double(30,30), cl.getEdge().getTarget().getLocation());
		assertEquals(20, cl.getTillEdgeEnd(), 1E-5);
		assertTrue(!cl.headingUpstream());
	}
	
	@Test
	public void testGetAndSetTickDistanceRemaining() {
		launchCox();
		assertEquals(0,cox.getTickDistanceRemaining(), 1E-5);
		cox.setTickDistanceRemaining(15.0);
		assertEquals(15,cox.getTickDistanceRemaining(), 1E-5);
	}
	
	@Test
	public void testTravellingTooSlowly() {
		launchCox();
		reset(mockBoat);
		expect(mockBoat.getGear()).andReturn(2).once();
		replay(mockBoat);
		assertTrue(cox.belowDesiredSpeed());
		verify(mockBoat);
		reset(mockBoat);
		expect(mockBoat.getGear()).andReturn(5).once();
		replay(mockBoat);
		assertFalse(cox.belowDesiredSpeed());
		verify(mockBoat);
	}
	
}
