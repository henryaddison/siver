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
	
	private River r;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		createLanes();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		r = new River(up,mid,down);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testRiver() {
		assertTrue(r.contains(10,10));
		assertTrue(r.contains(10,20));
		assertFalse(r.contains(10,70));
		assertFalse(r.contains(70,10));
	}
	
	@Test
	public void testLaneGetters() {
		assertEquals(up, r.getUpstream());
		assertEquals(mid, r.getMiddle());
		assertEquals(down, r.getDownstream());
	}
	
	@Test
	public void testGetLaneToLeftOfDownStream() {
		assertEquals(mid, r.getLaneToLeftOf(down, false));
		assertEquals(up, r.getLaneToLeftOf(mid, false));
	}
	
	@Test
	public void testGetLaneToRightOfDownStream() {
		assertEquals(down, r.getLaneToRightOf(mid, false));
		assertEquals(mid, r.getLaneToRightOf(up, false));
	}
	
	@Test
	public void testGetLaneToLeftOfUpStream() {
		assertEquals(mid, r.getLaneToLeftOf(up, true));
		assertEquals(down, r.getLaneToLeftOf(mid, true));
	}
	
	@Test
	public void testGetLaneToRightOfUpStream() {
		assertEquals(mid, r.getLaneToRightOf(down, true));
		assertEquals(up, r.getLaneToRightOf(mid, true));
	}
	
	@Test
	public void testGetLaneToLeftOfNoLanePresent() {
		assertNull(r.getLaneToLeftOf(up, false));
	}

	@Test
	public void testGetLaneToRightOfNoLanePresent() {
		assertNull(r.getLaneToRightOf(up, true));
	}
}
