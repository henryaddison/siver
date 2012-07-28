package siver.river.lane;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.random.RandomHelper;
import siver.agents.boat.CoxAgent;

public class LaneEdgeTest extends EdgeTester{
	
	private LaneEdge e;
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
		e = new LaneEdge(s,d);
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
		
		e.addCox(cox1);
		
		assertFalse(e.isEmpty());
		
		assertTrue(e.contains(cox1));
		assertFalse(e.contains(cox2));
		
		e.addCox(cox2);
		
		assertTrue(e.contains(cox1));
		assertTrue(e.contains(cox2));
		
		reset(cox1, cox2);
		expect(cox1.isIncapcitated()).andStubReturn(false);
		expect(cox2.isIncapcitated()).andStubReturn(false);
		replay(cox1, cox2);
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
	
	@Test
	public void testAddManyCoxes() {
		assertNull(e.getCrash());
		
		e.addCox(cox1);
		
		assertNull(e.getCrash());
		
		e.addCox(cox1);
		
		assertNull(e.getCrash());
		
		e.addCox(cox2);
		Crash crash =  e.getCrash();
		assertNotNull(crash);
		assertSame(e, crash.getEdge());
	}
	
	@Test
	public void testAddCoxToCrashedLane() {
		
		e.addCox(cox1);
		e.addCox(cox2);
		
		Crash crash =  e.getCrash();
		assertNotNull(crash);
		
		e.addCox(cox3);
		assertSame(crash, e.getCrash());
	}
	
	@Test
	public void testRemovingPenultimateCoxRemovesCrash() {		
		e.addCox(cox1);
		e.addCox(cox2);
		e.addCox(cox3);
		
		assertNotNull(e.getCrash());
		
		e.removeCox(cox1);
		assertNotNull(e.getCrash());
		
		e.removeCox(cox1);
		assertNotNull(e.getCrash());
		
		
		reset(cox3);
		expect(cox3.isIncapcitated()).andStubReturn(false);
		replay(cox3);
		e.removeCox(cox2);
		assertNull(e.getCrash());
	}

	@Test
	public void testIsTemporary() {
		assertTrue(!e.isTemporary());
	}
	
	@Test
	public void testCrashingCoxes() {
		CoxAgent first = createMock(CoxAgent.class);
		CoxAgent second = createMock(CoxAgent.class);
		
		replay(first);
		e.addCox(first);
		verify(first);
		
		reset(first);
		
		expect(first.getBoat()).andStubReturn(cox1.getBoat());
		expect(second.getBoat()).andStubReturn(cox2.getBoat());
		
		second.incapcitate();
		expectLastCall().once();
		first.incapcitate();
		expectLastCall().once();
		
		replay(first, second);
		e.addCox(second);
		verify(first, second);
	}
	
	@Test
	public void testWhenCoxLeavesNextCoxIsRecapcitated() {
		
	}
	
	@Test
	public void testPickRandom() {
		assertNull(e.pickRandomCox());
		
		e.addCox(cox1);
		assertSame(cox1, e.pickRandomCox());
		
		e.addCox(cox2);
		RandomHelper.setSeed(3);
		assertSame(cox2, e.pickRandomCox());
	}
}
