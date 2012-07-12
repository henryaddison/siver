package siver.river.lane;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LaneEdgeTest {

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
	public void testLaneEdge() {
		LaneNode s = new LaneNode(0,0,null);
		LaneNode d = new LaneNode(3,4,null);
		LaneEdge<LaneNode> e = new LaneEdge<LaneNode>(s,d);
		assertEquals(5.0, e.getWeight(), 1E-5);
	}
	
}
