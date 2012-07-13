package siver.agents.boat;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import siver.context.LaneContext;
import siver.river.lane.*;
import siver.river.lane.Lane.CompletedLaneException;
import siver.river.lane.Lane.UnstartedLaneException;

public class CoxLocationTest {
	private CoxLocation cl;
	private LaneEdge<LaneNode> edge;
	private LaneNode startNode, nextNode;
	private Lane lane;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		lane = new Lane(new LaneContext(), "Test Lane");
		lane.start(new Point2D.Double(0,0));
		lane.extend(0);
		lane.extend(Math.PI/4.0);
		startNode = lane.getStartNode();		
		edge = lane.getNextEdge(lane.getStartNode(), false);
		
		cl = new CoxLocation(edge, false);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCoxLocation() {
		CoxLocation cl = new CoxLocation(edge, 10, false);
		assertTrue(cl instanceof CoxLocation);
		assertEquals(lane, cl.getLane());
		assertEquals(edge, cl.getEdge());
		assertEquals(10, cl.getTillEdgeEnd(), 1E-5);
		assertTrue(!cl.headingUpstream());
	}
	
	@Test
	public void testCoxLocationWithoutDistance() {
		CoxLocation cl = new CoxLocation(edge, true);
		assertTrue(cl instanceof CoxLocation);
		assertEquals(lane, cl.getLane());
		assertEquals(edge, cl.getEdge());
		assertEquals(20, cl.getTillEdgeEnd(), 1E-5);
		assertTrue(cl.headingUpstream());
	}
	
	@Test
	public void testUpdateEdge() throws UnstartedLaneException, CompletedLaneException {
		LaneEdge<LaneNode> expEdge = lane.getNextEdge(edge.getTarget(), false);
		CoxLocation cl = new CoxLocation(edge, false);
		cl.updateEdge(expEdge);
		assertEquals(expEdge, cl.getEdge());
		assertEquals(20.0, cl.getTillEdgeEnd(), 1E-5);
	}
	
	
	@Test
	public void testMoveToEdgeEnd() {
		CoxLocation cl = new CoxLocation(edge, false);
		assertTrue(cl.getTillEdgeEnd() > 5);
		cl.moveToEdgeEnd();
		assertEquals(0,cl.getTillEdgeEnd(),1E-5);
		assertEquals(edge.getTarget(), cl.getNode());
	}
	
	@Test
	public void testMoveAlongEdge() {
		assertEquals(20, cl.getTillEdgeEnd(), 1E-5);
		cl.moveAlongEdge(3.5);
		assertEquals(16.5, cl.getTillEdgeEnd(), 1E-5);
		assertNull(cl.getNode());
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
	}
}


