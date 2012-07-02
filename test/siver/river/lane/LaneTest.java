/**
 * 
 */
package siver.river.lane;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author henryaddison
 *
 */
public class LaneTest {
	private static Lane l;
	
	private static ArrayList<Point2D.Double> exp_top, exp_mid, exp_bottom;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		l = new Lane(new Point2D.Double(0,10));
		
		exp_top = new ArrayList<Point2D.Double>();
		exp_mid = new ArrayList<Point2D.Double>();
		exp_bottom = new ArrayList<Point2D.Double>();
		
		exp_top.add(new Point2D.Double(0,20));
		exp_mid.add(new Point2D.Double(0,10));
		exp_bottom.add(new Point2D.Double(0,0));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link siver.river.lane.Lane#Lane(java.awt.geom.Point2D.Double)}.
	 */
	@Test
	public void testLane() {
		assertEquals(exp_top, l.getTop());
		assertEquals(exp_mid, l.getMid());
		assertEquals(exp_bottom, l.getBottom());
	}

	/**
	 * Test method for {@link siver.river.lane.Lane#add(double)}.
	 */
	@Test
	public void testAdd() {
		l.add(0);
		exp_top.add(new Point2D.Double(20, 20));
		exp_mid.add(new Point2D.Double(20, 10));
		exp_bottom.add(new Point2D.Double(20, 0));
		
		assertEquals(exp_top, l.getTop());
		assertEquals(exp_mid, l.getMid());
		assertEquals(exp_bottom, l.getBottom());
		
		l.add(Math.PI/2.0);
		exp_top.add(new Point2D.Double(10, 30));
		exp_mid.add(new Point2D.Double(20, 30));
		exp_bottom.add(new Point2D.Double(30, 30));
		
		assertEquals(exp_top, l.getTop());
		assertEquals(exp_mid, l.getMid());
		assertEquals(exp_bottom, l.getBottom());
	}

}
