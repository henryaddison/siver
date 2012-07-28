package siver.river.lane;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LaneChangeEdgeTest extends EdgeTester {
	private LaneChangeEdge e;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		LaneNode s = new LaneNode(0,0,null);
		LaneNode d = new LaneNode(3,4,null);
		e = new LaneChangeEdge(s,d, null, null);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddCox() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveCox() {
		fail("Not yet implemented");
	}

	@Test
	public void testLaneChangeEdge() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testIsTemporary() {
		assertTrue(e.isTemporary());
	}

}
