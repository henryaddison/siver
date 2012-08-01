package siver.boat;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.space.continuous.NdPoint;
import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.boat.Cox;
import siver.context.LaneContext;
import siver.river.River;
import siver.river.lane.*;
import siver.river.lane.Lane.CompletedLaneException;
import siver.river.lane.Lane.UnstartedLaneException;

public class BoatNavigationTest {
	
	public class TestCoxAgent extends Cox {
		public void setLocation(BoatNavigation cl) {
			navigator = cl;
		}
		
		public void setBoat(Boat b) {
			boat = b;
		}
	}

	private BoatNavigation cl;
	private TestCoxAgent cox;
	private LaneEdge edge;
	private Lane lane;
	private River river;
	private Boat boat;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		river = LaneTest.setupRiver();
		lane = river.getMiddle();
		edge = lane.getNextEdge(lane.getStartNode(), false);
		cox = new TestCoxAgent();
		boat = createMock(Boat.class);
		cox.setBoat(boat);
		boat.steerToward(edge.getNextNode(false).getLocation());
		expectLastCall().once();
		replay(boat);
		cl = new BoatNavigation(cox, boat, edge, false);
		reset(boat);
		cox.setLocation(cl);
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCoxLocationWithoutDistance() {
		boat.steerToward(edge.getNextNode(true).getLocation());
		expectLastCall().once();
		replay(boat);
		BoatNavigation cl = new BoatNavigation(cox, boat, edge, true);
		assertTrue(cl instanceof BoatNavigation);
		assertEquals(lane, cl.getLane());
		assertEquals(edge, cl.getEdge());
		assertEquals(20, cl.getTillEdgeEnd(), 1E-5);
		assertTrue(cl.headingUpstream());
	}
	
	@Test
	public void testUpdateEdge() throws UnstartedLaneException, CompletedLaneException {
		LaneEdge expEdge = lane.getNextEdge(edge.getTarget(), false);
		cox.getBoat().steerToward(expEdge.getNextNode(false).getLocation());
		expectLastCall().once();
		assertTrue(edge.contains(cox));
		assertTrue(!expEdge.contains(cox));
		replay(cox.getBoat());
		cl.updateEdge(expEdge);
		verify(cox.getBoat());
		assertEquals(expEdge, cl.getEdge());
		assertEquals(20.0, cl.getTillEdgeEnd(), 1E-5);
		
		assertTrue(!edge.contains(cox));
		assertTrue(expEdge.contains(cox));
	}
	
	@Test
	public void testUpdateEdgeDontUnoccupyCurrent() {
		LaneEdge expEdge = lane.getNextEdge(edge.getTarget(), false);
		cox.getBoat().steerToward(expEdge.getNextNode(false).getLocation());
		expectLastCall().once();
		
		assertTrue(edge.contains(cox));
		assertTrue(!expEdge.contains(cox));
		
		replay(cox.getBoat());
		cl.updateEdge(expEdge, false);
		verify(cox.getBoat());
		
		assertEquals(expEdge, cl.getEdge());
		assertEquals(20.0, cl.getTillEdgeEnd(), 1E-5);
		
		assertTrue(edge.contains(cox));
		assertTrue(expEdge.contains(cox));
	}
	
	@Test
	public void testMoveToEdgeEnd() {
		assertTrue(cl.getTillEdgeEnd() > 5);
		cox.getBoat().move(20.0);
		expectLastCall().once();
		replay(cox.getBoat());
		cl.moveToEdgeEnd();
		verify(cox.getBoat());
		assertEquals(0,cl.getTillEdgeEnd(),1E-5);
	}
	
	@Test
	public void testMoveAlongEdge() {
		assertEquals(20, cl.getTillEdgeEnd(), 1E-5);
		cox.getBoat().move(3.5);
		expectLastCall().once();
		replay(cox.getBoat());
		cl.moveAlongEdge(3.5);
		verify(cox.getBoat());
		assertEquals(16.5, cl.getTillEdgeEnd(), 1E-5);
	}
	
	@Test
	public void testToggleUpstream() {
		assertTrue(!cl.headingUpstream());
		replay(cox.getBoat());
		cl.toggleUpstream();
		verify(cox.getBoat());
		assertTrue(cl.headingUpstream());
	}
	
	@Test
	public void testGetDestinationNode() {
		assertEquals(edge.getTarget(), cl.getDestinationNode());
		replay(cox.getBoat());
		cl.toggleUpstream();
		verify(cox.getBoat());
		assertEquals(edge.getSource(), cl.getDestinationNode());
	}
	
	@Test
	public void testChangingLane() {
		LaneEdge mockEdge= createMock(LaneEdge.class);
		
		assertTrue(!cl.changingLane());
		LaneChangeEdge change_edge = new LaneChangeEdge(new LaneNode(10,10,river.getUpstream()), new LaneNode(30,20, river.getUpstream()), mockEdge , mockEdge);
		
		Boat boat = cox.getBoat();
		boat.steerToward(change_edge.getNextNode(false).getLocation());
		expectLastCall().once();
		
		mockEdge.addCox(cox);
		expectLastCall().times(2);
		
		
		replay(boat);
		replay(mockEdge);
		cl.updateEdge(change_edge, false);
		verify(cox.getBoat());
		verify(mockEdge);
		assertTrue(cl.changingLane());
	}
}


