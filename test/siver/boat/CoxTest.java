package siver.boat;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.space.continuous.ContinuousSpace;
import siver.agents.boat.actions.LetBoatRun;
import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.boat.Cox;
import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class CoxTest {
	
	private Cox cox;
	private Boat mockBoat;
	private Lane mockLane;
	private ContinuousSpace<Object> mockSpace;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mockBoat = createMock(Boat.class);
		mockLane = createMock(Lane.class);
		mockSpace = createMock(ContinuousSpace.class);
		cox = new Cox();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCoxAgent() {
		assertNotNull(cox);
		assertTrue(cox instanceof Cox);
	}

	@Test
	public void testLaunch() {
		launchCox();
	}
	
	private void launchCox() {
		Point2D.Double expLoc = new Point2D.Double(10,30);
		LaneNode expNode = new LaneNode(expLoc, mockLane);
		LaneNode nextNode = new LaneNode(30,30, mockLane);
		expect(mockLane.getStartNode()).andStubReturn(expNode);
		mockBoat.launch(cox, expLoc);
		expectLastCall().once();
		expect(mockLane.getNextEdge(expNode, false)).andReturn(new LaneEdge(expNode, nextNode)).once();
		mockBoat.steerToward(nextNode.getLocation());
		expectLastCall().once();
		
		replay(mockBoat);
		replay(mockLane);
		replay(mockSpace);
		try {
			cox.launch(mockBoat, mockLane, 8);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("error launching");
		}
		verify(mockBoat);
		verify(mockLane);
		verify(mockSpace);
		
		assertEquals(nextNode, cox.getNavigator().getDestinationNode());
		
		reset(mockLane);
		reset(mockSpace);
		reset(mockBoat);
	}

	@Test
	public void testStep() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		launchCox();
		
		Point2D.Double expLoc = new Point2D.Double(10,30);
		LaneNode expNode = new LaneNode(expLoc, mockLane);
		LaneNode nextNode = new LaneNode(30,30, mockLane);
		LaneNode furtherNode = new LaneNode(50,30, mockLane);
		
		expect(mockBoat.getGear()).andReturn(10).times(1);
		
		expect(mockLane.getStartNode()).andStubReturn(expNode);
		expect(mockLane.getNextEdge(cox.getNavigator().getDestinationNode(), false)).andReturn(new LaneEdge(cox.getNavigator().getDestinationNode(), furtherNode)).once();
		
		replay(mockLane);
		replay(mockBoat);
		cox.step();
		verify(mockBoat);
		verify(mockLane);
		
		assertNull(cox.getAction());
	}
	
	@Test
	public void testStepIncapcitated() throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		launchCox();
		
		mockBoat.deadStop();
		expectLastCall().once();
		
		replay(mockBoat);
		cox.incapcitate();
		verify(mockBoat);
		reset(mockBoat);
		replay(mockBoat);
		cox.step();
		verify(mockBoat);
	}
	
	@Test
	public void testGetAction() {
		assertNull(cox.getAction());
	}
	
	@Test
	public void testGetBoat() {
		launchCox();
		assertEquals(mockBoat, cox.getBoat());
	}
	
	@Test
	public void testGetLocation() {
		launchCox();
		BoatNavigation cl = cox.getNavigator();
		assertEquals(mockLane, cl.getLane());
		assertEquals(new Point2D.Double(10,30), cl.getEdge().getSource().getLocation());
		assertEquals(new Point2D.Double(30,30), cl.getEdge().getTarget().getLocation());
		assertEquals(20, cl.getTillEdgeEnd(), 1E-5);
		assertTrue(!cl.headingUpstream());
	}
	
	
	

	
	@Test
	public void testIncapcitate() {
		launchCox();
		assertTrue(!cox.isIncapcitated());
		
		reset(mockBoat);
		mockBoat.deadStop();
		expectLastCall().once();
		
		replay(mockBoat);
		cox.incapcitate();
		verify(mockBoat);
		
		assertTrue(cox.isIncapcitated());
	}
	
	@Test
	public void testRecapcitate() {
		launchCox();
		reset(mockBoat);
		mockBoat.deadStop();
		expectLastCall().once();
		
		replay(mockBoat);
		cox.incapcitate();
		
		assertTrue(cox.isIncapcitated());
		
		cox.recapcitate();
		
		assertTrue(!cox.isIncapcitated());
	}
}
