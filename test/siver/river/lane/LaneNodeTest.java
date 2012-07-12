package siver.river.lane;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import siver.context.LaneContext;

public class LaneNodeTest {

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
	public void testLaneNodeDoubleDouble() {
		LaneNode ln = new LaneNode(1.5, 6.7, null);
		assertEquals(new Point2D.Double(1.5,6.7), ln.getLocation());
	}

	@Test
	public void testLaneNodeDouble() {
		Point2D.Double exp_loc = new Point2D.Double(1.5,6.7);
		LaneNode ln = new LaneNode(exp_loc, null);
		assertEquals(exp_loc, ln.getLocation());
	}

	@Test
	public void testDistance() {
		LaneNode ln = new LaneNode(0,0,null);
		LaneNode otherln = new LaneNode(3,4,null);
		assertEquals(0, ln.distance(ln), 1E-5);
		assertEquals(5, ln.distance(otherln), 1E-5);
		assertEquals(5, otherln.distance(ln), 1E-5);
	}
	
	@Test
	public void testLaneNodeWithLane() {
		Lane testLane = new Lane(new LaneContext(), "Test lane");
		LaneNode ln = new LaneNode(1,2, testLane);
		assertEquals(testLane, ln.getLane());
	}
	
	@Test
	public void testLaneNodeDoubleWithLane() {
		Lane testLane = new Lane(new LaneContext(), "Test lane");
		LaneNode ln = new LaneNode(new Point2D.Double(12,13), testLane);
		assertEquals(testLane, ln.getLane());
	}

}
