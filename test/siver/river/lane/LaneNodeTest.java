package siver.river.lane;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.space.continuous.NdPoint;
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
	public void testLaneNodeNdPoint() {
		Point2D.Double exp_loc = new Point2D.Double(1.5,6.7);
		NdPoint pt = new NdPoint(1.5, 6.7);
		LaneNode ln = new LaneNode(pt, null);
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
		Lane testLane = new Lane(new LaneContext("Test Context"), "Test lane");
		LaneNode ln = new LaneNode(1,2, testLane);
		assertEquals(testLane, ln.getLane());
	}
	
	@Test
	public void testLaneNodeDoubleWithLane() {
		Lane testLane = new Lane(new LaneContext("Test Context"), "Test lane");
		LaneNode ln = new LaneNode(new Point2D.Double(12,13), testLane);
		assertEquals(testLane, ln.getLane());
	}
	
	@Test
	public void testDistanceToNdPoint() {
		LaneNode ln = new LaneNode(0,0,null);
		NdPoint pt = new NdPoint(3,4);
		NdPoint otherpt = new NdPoint(-3,-4);
		NdPoint anotherpt = new NdPoint(10,0);
		
		
		assertEquals(0, ln.distance(ln.toNdPoint()), 1E-5);
		assertEquals(5, ln.distance(pt), 1E-5);
		
		assertEquals(5, ln.distance(otherpt), 1E-5);
		assertEquals(10, ln.distance(anotherpt), 1E-5);
	}
	
	@Test
	public void testToNdPoint() {
		LaneNode ln = new LaneNode(3,5,null);
		
		assertEquals(new NdPoint(3,5), ln.toNdPoint());
	}

}
