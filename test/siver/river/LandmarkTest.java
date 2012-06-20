package siver.river;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.space.grid.GridPoint;
import siver.river.Landmark;

public class LandmarkTest {

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
	public void testLandmark() {
		Landmark l = new Landmark(new GridPoint(0,0), new GridPoint(10,10));
	}

	@Test
	public void testGetLeft() {
		Landmark l = new Landmark(new GridPoint(0,0), new GridPoint(10,10));
		assertEquals(l.getLeft(), new GridPoint(0,0));
	}

	@Test
	public void testGetRight() {
		Landmark l = new Landmark(new GridPoint(0,0), new GridPoint(10,10));
		assertEquals(l.getLeft(), new GridPoint(0,0));
	}

	@Test
	public void testGetLocation() {
		Landmark l = new Landmark(new GridPoint(0,0), new GridPoint(10,10));
		assertEquals(l.getLocation(), new GridPoint(5,5));
	}
	
	@Test
	public void testEquals() {
		Landmark l = new Landmark(new GridPoint(0,0), new GridPoint(10,10));
		Landmark l1 = new Landmark(new GridPoint(0,0), new GridPoint(10,10));
		Landmark l2 = new Landmark(new GridPoint(10,0), new GridPoint(10,10));
		
		assertEquals(l, l1);
		assertFalse(l.equals(l2));
	}
}
