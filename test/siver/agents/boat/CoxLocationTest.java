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
	private CoxAgent cox;
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
		lane = new Lane(new LaneContext("Test Context"), "Test Lane");
		lane.start(new Point2D.Double(0,0));
		lane.extend(0);
		lane.extend(Math.PI/4.0);
		startNode = lane.getStartNode();		
		edge = lane.getNextEdge(lane.getStartNode(), false);
		cox = new CoxAgent();
		cl = new CoxLocation(cox, edge, false);
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
		cl.updateEdge(expEdge);
		assertEquals(expEdge, cl.getEdge());
		assertEquals(20.0, cl.getTillEdgeEnd(), 1E-5);
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
	}
}


