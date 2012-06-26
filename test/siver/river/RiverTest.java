package siver.river;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.space.grid.GridPoint;
import siver.river.Landmark;
import siver.river.River;

public class RiverTest {

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
	public void testAdd() {
		River r = new River();
		assertEquals(r.getLandmarks().size(), 0);
		r.add(new Landmark(new Point2D.Double(0,0), new Point2D.Double(10,0)));
		assertEquals(r.getLandmarks().size(), 1);
		assertEquals(r.getLandmarks().get(0), new Landmark(new Point2D.Double(0,0), new Point2D.Double(10,0)));
		r.add(new Landmark(new Point2D.Double(0,10), new Point2D.Double(10,10)));
		assertEquals(r.getLandmarks().size(), 2);
		assertEquals(r.getLandmarks().get(0), new Landmark(new Point2D.Double(0,0), new Point2D.Double(10,0)));
		assertEquals(r.getLandmarks().get(1), new Landmark(new Point2D.Double(0,10), new Point2D.Double(10,10)));
	}

	@Test
	public void testContains() {
		River r = new River();
		r.complete();
		assertFalse(r.contains(5,5));
		assertFalse(r.contains(15,15));
		r.add(new Landmark(new Point2D.Double(0,0), new Point2D.Double(10,0)));
		r.complete();
		assertFalse(r.contains(5,5));
		assertFalse(r.contains(15,15));
		r.add(new Landmark(new Point2D.Double(0,10), new Point2D.Double(10,10)));
		r.complete();
		assertTrue(r.contains(5,5));
		assertFalse(r.contains(15,15));
		r.add(new Landmark(new Point2D.Double(20,20), new Point2D.Double(20,10)));
		r.complete();
		assertTrue(r.contains(5,5));
		assertTrue(r.contains(15,15));
		assertTrue(r.contains(new Point2D.Double(5,5)));
		assertFalse(r.contains(new Point2D.Double(30,30)));
	}

}
