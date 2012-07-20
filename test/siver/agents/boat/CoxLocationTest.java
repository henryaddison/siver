package siver.agents.boat;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.space.continuous.NdPoint;
import siver.context.LaneContext;
import siver.river.River;
import siver.river.lane.*;
import siver.river.lane.Lane.CompletedLaneException;
import siver.river.lane.Lane.UnstartedLaneException;

public class CoxLocationTest {
	
	public class TestCoxAgent extends CoxAgent {
		public void setLocation(CoxLocation cl) {
			location = cl;
		}
		
		public void setBoat(BoatAgent b) {
			boat = b;
		}
	}

	private CoxLocation cl;
	private TestCoxAgent cox;
	private LaneEdge<LaneNode> edge;
	private Lane lane;
	private River river;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		river = LaneTest.setupRiver();
		lane = river.getMiddle();
		edge = lane.getNextEdge(lane.getStartNode(), false);
		cox = new TestCoxAgent();
		cl = new CoxLocation(cox, edge, false);
		cox.setLocation(cl);
		BoatAgent boat = createMock(BoatAgent.class);
		cox.setBoat(boat);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCoxLocationWithoutDistance() {
		CoxLocation cl = new CoxLocation(cox, edge, true);
		assertTrue(cl instanceof CoxLocation);
		assertEquals(lane, cl.getLane());
		assertEquals(edge, cl.getEdge());
		assertEquals(20, cl.getTillEdgeEnd(), 1E-5);
		assertTrue(cl.headingUpstream());
	}
	
	@Test
	public void testUpdateEdge() throws UnstartedLaneException, CompletedLaneException {
		LaneEdge<LaneNode> expEdge = lane.getNextEdge(edge.getTarget(), false);
		CoxLocation cl = new CoxLocation(cox, edge, false);
		assertTrue(edge.contains(cox));
		assertTrue(!expEdge.contains(cox));
		cl.updateEdge(expEdge);
		assertEquals(expEdge, cl.getEdge());
		assertEquals(20.0, cl.getTillEdgeEnd(), 1E-5);
		
		assertTrue(!edge.contains(cox));
		assertTrue(expEdge.contains(cox));
	}
	
	@Test
	public void testUpdateEdgeDontUnoccupyCurrent() {
		LaneEdge<LaneNode> expEdge = lane.getNextEdge(edge.getTarget(), false);
		CoxLocation cl = new CoxLocation(cox, edge, false);
		assertTrue(edge.contains(cox));
		assertTrue(!expEdge.contains(cox));
		cl.updateEdge(expEdge, false);
		assertEquals(expEdge, cl.getEdge());
		assertEquals(20.0, cl.getTillEdgeEnd(), 1E-5);
		
		assertTrue(edge.contains(cox));
		assertTrue(expEdge.contains(cox));
	}
	
	@Test
	public void testMoveToEdgeEnd() {
		CoxLocation cl = new CoxLocation(cox, edge, false);
		assertTrue(cl.getTillEdgeEnd() > 5);
		cl.moveToEdgeEnd();
		assertEquals(0,cl.getTillEdgeEnd(),1E-5);
	}
	
	@Test
	public void testMoveAlongEdge() {
		assertEquals(20, cl.getTillEdgeEnd(), 1E-5);
		cl.moveAlongEdge(3.5);
		assertEquals(16.5, cl.getTillEdgeEnd(), 1E-5);
	}
	
	@Test
	public void testToggleUpstream() {
		assertTrue(!cl.headingUpstream());
		cl.toggleUpstream();
		assertTrue(cl.headingUpstream());
	}
	
	@Test
	public void testGetDestinationNode() {
		assertEquals(edge.getTarget(), cl.getDestinationNode());
		cl.toggleUpstream();
		assertEquals(edge.getSource(), cl.getDestinationNode());
	}
	
	@Test
	public void testChangingLane() {
		assertTrue(!cl.changingLane());
		LaneChangeEdge<LaneNode> change_edge = new LaneChangeEdge<LaneNode>(new LaneNode(10,10,river.getUpstream()), new LaneNode(30,20, river.getUpstream()), lane);
		
		BoatAgent boat = cox.getBoat();
		expect(boat.getLocation()).andReturn(new NdPoint(15,20)).once();
		replay(boat);
		cl.updateEdge(change_edge, false);
		verify(cox.getBoat());
		assertTrue(cl.changingLane());
	}
}


