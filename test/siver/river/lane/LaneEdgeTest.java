package siver.river.lane;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import siver.agents.boat.BoatAgent;
import siver.agents.boat.CoxAgent;
import siver.context.SiverContextCreator;

public class LaneEdgeTest {
	private Context<Object> mockContext;
	private ContinuousSpace<Object> mockSpace;
	private CoxAgent cox1, cox2, cox3;
	private LaneEdge<LaneNode> e;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mockContext = createMock(Context.class);
		
		mockSpace = createMock(ContinuousSpace.class);
		
		SiverContextCreator.setContext(mockContext);
		SiverContextCreator.setSpace(mockSpace);
		
		expect(mockContext.add(anyObject())).andStubReturn(true);
		expect(mockSpace.moveTo(anyObject(), eq(10.0), eq(20.0))).andStubReturn(true);
		expect(mockContext.remove(anyObject())).andStubReturn(true);
		
		
		cox1 = createMock(CoxAgent.class);
		cox2 = createMock(CoxAgent.class);
		cox3 = createMock(CoxAgent.class);
		
		BoatAgent mockBoat = createMock(BoatAgent.class);
		expect(mockBoat.getLocation()).andStubReturn(new NdPoint(10,20));
		expect(cox1.getBoat()).andStubReturn(mockBoat);
		expect(cox2.getBoat()).andStubReturn(mockBoat);
		expect(cox3.getBoat()).andStubReturn(mockBoat);
		
		replay(cox1, cox2, cox3, mockBoat, mockContext, mockSpace);
		
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
		
		e.removeCox(cox2);
		assertNull(e.getCrash());
	}
	
}
