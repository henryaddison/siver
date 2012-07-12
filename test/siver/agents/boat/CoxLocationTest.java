package siver.agents.boat;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import siver.context.LaneContext;
import siver.river.lane.*;

public class CoxLocationTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCoxLocation() {
		Lane exp_lane = new Lane(new LaneContext(), "Test Lane");
		LaneEdge<LaneNode> exp_edge = new LaneEdge<LaneNode>(new LaneNode(0,30, exp_lane), new LaneNode(20,30, exp_lane));
		CoxLocation cl = new CoxLocation(exp_edge, 20);
		assertTrue(cl instanceof CoxLocation);
		assertEquals(exp_lane, cl.getLane());
		assertEquals(exp_edge, cl.getEdge());
		assertEquals(20, cl.getTillEdgeEnd(), 1E-5);
	}
	
	@Test
	public void testCoxLocationWithoutDistance() {
		Lane exp_lane = new Lane(new LaneContext(), "Test Lane");
		LaneEdge<LaneNode> exp_edge = new LaneEdge<LaneNode>(new LaneNode(0,30, exp_lane), new LaneNode(20,30, exp_lane));
		CoxLocation cl = new CoxLocation(exp_edge);
		assertTrue(cl instanceof CoxLocation);
		assertEquals(exp_lane, cl.getLane());
		assertEquals(exp_edge, cl.getEdge());
		assertEquals(20, cl.getTillEdgeEnd(), 1E-5);
	}
	
}
