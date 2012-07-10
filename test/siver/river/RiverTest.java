package siver.river;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import siver.LanedTest;
import siver.river.River;

public class RiverTest extends LanedTest {
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		createLanes();
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
	public void testRiver() {
		River r = new River(up,mid,down);
		assertTrue(r.contains(10,10));
		assertTrue(r.contains(10,20));
		assertFalse(r.contains(10,70));
		assertFalse(r.contains(70,10));
	}
	
	@Test
	public void testLaneGetters() {
		River r = new River(up,mid,down);
		assertEquals(up, r.getUpstream());
		assertEquals(mid, r.getMiddle());
		assertEquals(down, r.getDownstream());
	}

}
