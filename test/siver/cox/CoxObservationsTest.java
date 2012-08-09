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
import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class CoxObservationsTest {
	Boat mboat;
	Cox mcox;
	BoatNavigation mnav;
	CoxObservations obs;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	}
	@Test
	public void testNearbyBoatInfrontNoBoatsInfront() {
		setupMocksForNearbyBoatInfront();
		
		expect(mnode.getLane()).andReturn(mlane).times(3);
		expect(mlane.getNextEdge(mnode, false)).andReturn(medge).times(3);
		expect(medge.getNextNode(false)).andReturn(mnode).times(3);
		expect(medge.isEmpty()).andReturn(true).times(3);
		
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
}
