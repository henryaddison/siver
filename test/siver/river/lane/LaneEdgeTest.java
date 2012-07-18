package siver.river.lane;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import siver.agents.boat.CoxAgent;

public class LaneEdgeTest {
	private LaneEdge<LaneNode> e;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		LaneNode s = new LaneNode(0,0,null);
		LaneNode d = new LaneNode(3,4,null);
		e = new LaneEdge<LaneNode>(s,d);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLaneEdge() {
		assertEquals(5.0, e.getWeight(), 1E-5);
	}
	
	@Test
	public void testAddRemoveCoxes() {
		assertTrue(e.isEmpty());
		CoxAgent cox1 = new CoxAgent();
		CoxAgent cox2 = new CoxAgent();
		
		e.addCox(cox1);
		
		assertFalse(e.isEmpty());
		
		assertTrue(e.contains(cox1));
		assertFalse(e.contains(cox2));
		
		e.addCox(cox2);
		
		assertTrue(e.contains(cox1));
		assertTrue(e.contains(cox2));
		
		e.removeCox(cox1);
		
		assertFalse(e.contains(cox1));
		assertTrue(e.contains(cox2));
		
		//check no harm in removing cox more than once
		
		e.removeCox(cox1);
		
		assertFalse(e.contains(cox1));
		assertTrue(e.contains(cox2));
		
		
		//make sure cox can only be added once
		e.addCox(cox2);
		
		assertFalse(e.contains(cox1));
		assertTrue(e.contains(cox2));
		
		e.removeCox(cox2);
		
		assertFalse(e.contains(cox1));
		assertFalse(e.contains(cox2));
	}
	
}
