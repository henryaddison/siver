package siver.cox;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.cox.Cox;
import siver.river.River;
import siver.river.RiverFactory;
import siver.river.lane.Lane;
import siver.river.lane.Lane.NoNextNode;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;
import siver.river.lane.LaneTest;

public class CoxObservationsTest {
	Boat mboat;
	Cox mcox;
	BoatNavigation mnav;
	CoxObservations obs;
	private static River r;	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		r = LaneTest.setupRiver();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mboat = createMock(Boat.class);
		mcox = createMock(Cox.class);
		mnav = createMock(BoatNavigation.class);
		obs = new CoxObservations(mcox, mboat, mnav);
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	private void replayMocks() {
		replay(mboat, mnav, mcox);
	}
	
	private void verifyAndResetMocks() {
		verify(mboat, mnav, mcox);
		reset(mboat, mnav, mcox);
	}

	@Test
	public void testCoxObservations() {
		Boat mboat = createMock(Boat.class);
		Cox mcox = createMock(Cox.class);
		BoatNavigation mnav = createMock(BoatNavigation.class);
		new CoxObservations(mcox, mboat, mnav);
	}
	
	@Test
	public void testTravellingTooSlowly() {
		expect(mcox.desired_gear()).andStubReturn(5);
		expect(mboat.getGear()).andReturn(2).once();
		
		replayMocks();
		assertTrue(obs.belowDesiredSpeed());
		verifyAndResetMocks();
		
		expect(mcox.desired_gear()).andStubReturn(5);
		expect(mboat.getGear()).andReturn(9).once();
		
		replayMocks();
		assertFalse(obs.belowDesiredSpeed());
		verifyAndResetMocks();
	}
	
	LaneNode mnode;
	LaneEdge medge;
	Lane mlane;
	
	private void setupMocksForNearbyBoatInfront() {
		mnode = createMock(LaneNode.class);
		medge = createMock(LaneEdge.class);
		mlane = createMock(Lane.class);
		
		expect(mnav.headingUpstream()).andStubReturn(false);
		expect(mnav.getDestinationNode()).andReturn(mnode).times(1);
		expect(mnav.getLane()).andStubReturn(mlane);
	}
	
	@Test
	public void testNearbyBoatInfrontNoBoatsInfront() {
		setupMocksForNearbyBoatInfront();
		
		expect(mnav.getLane()).andReturn(mlane).once();
		expect(mnode.getLane()).andReturn(mlane).times(4);
		expect(mlane.getNextEdge(mnode, false)).andReturn(medge).times(4);
		expect(medge.getNextNode(false)).andReturn(mnode).times(4);
		expect(medge.isEmpty()).andReturn(true).times(4);
		
		replay(mnode, medge, mlane);
		replayMocks();
		assertFalse(obs.nearbyBoatInfront());
		verifyAndResetMocks();
		verify(mnode, medge, mlane);
	}
	
	@Test
	public void testNearbyBoatInfrontNoBoatsNearInfront() {
		setupMocksForNearbyBoatInfront();
		
		expect(mnav.getLane()).andReturn(mlane).once();
		expect(mnode.getLane()).andReturn(mlane).times(4);
		expect(mlane.getNextEdge(mnode, false)).andReturn(medge).times(4);
		expect(medge.getNextNode(false)).andReturn(mnode).times(3);
		expect(medge.isEmpty()).andReturn(true).times(3).andReturn(false).once();
		
		replay(mnode, medge, mlane);
		replayMocks();
		assertFalse(obs.nearbyBoatInfront());
		verifyAndResetMocks();
		verify(mnode, medge, mlane);
	}
	
	@Test
	public void testNearbyBoatInfrontBoatsInfront() {
		setupMocksForNearbyBoatInfront();
		
		expect(mnode.getLane()).andReturn(mlane).times(1);
		expect(mlane.getNextEdge(mnode, false)).andReturn(medge).times(1);
		expect(medge.isEmpty()).andReturn(false).times(1);
		
		replay(mnode, medge, mlane);
		replayMocks();
		assertTrue(obs.nearbyBoatInfront());
		verifyAndResetMocks();
		verify(mnode, medge, mlane);
	}
	
	@Test
	public void testNearbyBoatInfrontNotDirectlyInfront() {
		setupMocksForNearbyBoatInfront();
		
		expect(mnode.getLane()).andReturn(mlane).times(2);
		expect(mlane.getNextEdge(mnode, false)).andReturn(medge).times(2);
		expect(medge.getNextNode(false)).andReturn(mnode).times(1);
		expect(medge.isEmpty()).andReturn(true).times(1).andReturn(false).times(1);
		
		replay(mnode, medge, mlane);
		replayMocks();
		assertTrue(obs.nearbyBoatInfront());
		verifyAndResetMocks();
		verify(mnode, medge, mlane);
	}
	
	@Test
	public void testNearbyBoatInfrontNoBoatsAtRiverEnd() {
		setupMocksForNearbyBoatInfront();
		
		expect(mnode.getLane()).andReturn(mlane).times(2);
		expect(mlane.getNextEdge(mnode, false)).andReturn(medge).times(1).andReturn(null).times(1);
		expect(medge.getNextNode(false)).andReturn(mnode).times(1);
		expect(medge.isEmpty()).andReturn(true).times(1);
		
		
		replay(mnode, medge, mlane);
		replayMocks();
		assertFalse(obs.nearbyBoatInfront());
		verifyAndResetMocks();
		verify(mnode, medge, mlane);
	}
	
	@Test
	public void testOutingOverNotCoveredDistance() throws NoNextNode {
		expect(mcox.getGoalDistance()).andReturn(1000.0).once();
		expect(mboat.total_distance_covered()).andReturn(0.0).once();
		
		replayMocks();
		assertFalse(obs.outingOver());
		verifyAndResetMocks();
	}
	
	@Test
	public void testOutingOverNotBackAtBoatHouse() throws NoNextNode {
		expect(mcox.getGoalDistance()).andReturn(1000.0).once();
		expect(mboat.total_distance_covered()).andReturn(2000.0).once();
		Lane l = r.downstream_lane();
		LaneNode n = l.getNextNode(l.getStartNode(), false);
				
		expect(mnav.getDestinationNode()).andReturn(n);
		expect(mnav.getLane()).andReturn(l);
		
		replayMocks();
		assertFalse(obs.outingOver());
		verifyAndResetMocks();
	}
	
	@Test
	public void testOutingOver() {
		expect(mcox.getGoalDistance()).andReturn(1000.0).once();
		expect(mboat.total_distance_covered()).andReturn(2000.0).once();
		Lane l = r.downstream_lane();
		LaneNode n = l.getStartNode();
				
		expect(mnav.getDestinationNode()).andReturn(n);
		expect(mnav.getLane()).andReturn(l);
		
		replayMocks();
		assertTrue(obs.outingOver());
		verifyAndResetMocks();
	}
}
