package siver.river.lane;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LaneEdgeCreatorTest {
	private static LaneEdgeCreator<LaneNode> lec;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		lec = new LaneEdgeCreator<LaneNode>();
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
	public void testGetEdgeType() {
		assertEquals(LaneEdge.class, lec.getEdgeType());
	}

	@Test
	public void testCreateEdge() {
		LaneNode s = new LaneNode(0,0,null,Lane.DEFAULT_OPACITY);
		LaneNode d = new LaneNode(3,4,null,Lane.DEFAULT_OPACITY);
		LaneEdge edge = lec.createEdge(s, d, false, 10);
		assertEquals(5.0,edge.getWeight(), 1E-5);
		assertTrue(edge.isDirected());
		assertEquals(s, edge.getSource());
		assertEquals(d, edge.getTarget());
	}

}
