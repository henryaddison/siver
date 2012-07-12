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
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		LaneEdge<LaneNode> edge = new LaneEdge<LaneNode>(new LaneNode(0,0,null), new LaneNode(10,0, null));
		cl = new CoxLocation(edge, false);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCoxLocation() {
		Lane exp_lane = new Lane(new LaneContext(), "Test Lane");
		LaneEdge<LaneNode> exp_edge = new LaneEdge<LaneNode>(new LaneNode(0,30, exp_lane), new LaneNode(20,30, exp_lane));
		CoxLocation cl = new CoxLocation(exp_edge, 10, false);
		assertTrue(cl instanceof CoxLocation);
		assertEquals(exp_lane, cl.getLane());
		assertEquals(exp_edge, cl.getEdge());
		assertEquals(10, cl.getTillEdgeEnd(), 1E-5);
		assertTrue(!cl.headingUpstream());
	}
	
	@Test
	public void testCoxLocationWithoutDistance() {
		Lane exp_lane = new Lane(new LaneContext(), "Test Lane");
		LaneEdge<LaneNode> exp_edge = new LaneEdge<LaneNode>(new LaneNode(0,30, exp_lane), new LaneNode(20,30, exp_lane));
		CoxLocation cl = new CoxLocation(exp_edge, true);
		assertTrue(cl instanceof CoxLocation);
		assertEquals(exp_lane, cl.getLane());
		assertEquals(exp_edge, cl.getEdge());
		assertEquals(20, cl.getTillEdgeEnd(), 1E-5);
		assertTrue(cl.headingUpstream());
	}
	
	@Test
	public void testUpdateEdge() throws UnstartedLaneException, CompletedLaneException {
		Lane exp_lane = new Lane(new LaneContext(), "Test Lane");
		exp_lane.start(new Point2D.Double(0,0));
		exp_lane.extend(0);
		exp_lane.extend(Math.PI/4.0);
		
		LaneEdge<LaneNode> startEdge = exp_lane.getNextEdge(exp_lane.getStartNode(), false);
		LaneEdge<LaneNode> expEdge = exp_lane.getNextEdge(startEdge.getTarget(), false);
		CoxLocation cl = new CoxLocation(startEdge, false);
		cl.updateEdge(expEdge);
		assertEquals(expEdge, cl.getEdge());
		assertEquals(20.0, cl.getTillEdgeEnd(), 1E-5);
	}
	
	
	@Test
	public void testMoveToEdgeEnd() {
		LaneEdge<LaneNode> edge = new LaneEdge<LaneNode>(new LaneNode(0,0,null), new LaneNode(10,10, null));
		CoxLocation cl = new CoxLocation(edge, false);
		assertTrue(cl.getTillEdgeEnd() > 5);
		cl.moveToEdgeEnd();
		assertEquals(0,cl.getTillEdgeEnd(),1E-5);
	}
	
	@Test
	public void testMoveAlongEdge() {
		
		assertEquals(10, cl.getTillEdgeEnd(), 1E-5);
		cl.moveAlongEdge(3.5);
		assertEquals(6.5, cl.getTillEdgeEnd(), 1E-5);
	}
	
	@Test
	public void testToggleUpstream() {
		assertTrue(!cl.headingUpstream());
		cl.toggleUpstream();
		assertTrue(cl.headingUpstream());
	}
}
