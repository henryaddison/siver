package siver.cox;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.space.continuous.NdPoint;
import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.context.SiverContextCreator;
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
		SiverContextCreator.setRiver(r);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mboat = createMock(Boat.class);
		mcox = createMock(Cox.class);
		mnav = createMock(BoatNavigation.class);
		
		expect(mnav.getLane()).andStubReturn(r.middle_lane());
		expect(mnav.getDestinationNode()).andStubReturn(r.middle_lane().getStartNode());
		expect(mnav.headingUpstream()).andStubReturn(false);
		expect(mboat.getLocation()).andStubReturn(new NdPoint(10,10));
		
		replay(mboat, mcox, mnav);
		obs = new CoxObservations(mcox, mboat, mnav);
		verify(mboat, mcox, mnav);
		reset(mboat, mcox, mnav);
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
		assertNotNull(obs);
	}
	
	@Test
	public void testTravellingTooSlowly() {
		expect(mcox.desired_gear()).andStubReturn(5);
		expect(mboat.getGear()).andReturn(2).once();
		
		replayMocks();
		obs.freezeBelowDesiredSpeed();
		assertTrue(obs.belowDesiredSpeed());
		verifyAndResetMocks();
		
		expect(mcox.desired_gear()).andStubReturn(5);
		expect(mboat.getGear()).andReturn(9).once();
		
		replayMocks();
		obs.freezeBelowDesiredSpeed();
		assertFalse(obs.belowDesiredSpeed());
		verifyAndResetMocks();
	}
	
	@Test
	public void testOutingOverNotCoveredDistance() throws NoNextNode {
		expect(mcox.getGoalDistance()).andReturn(1000.0).once();
		expect(mboat.total_distance_covered()).andReturn(0.0).once();
		
		replayMocks();
		obs.freezeOutingOver();
		verifyAndResetMocks();
		assertFalse(obs.outingComplete());
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
		obs.freezeOutingOver();
		verifyAndResetMocks();
		assertFalse(obs.outingComplete());
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
		obs.freezeOutingOver();
		verifyAndResetMocks();
		assertTrue(obs.outingComplete());
	}
}
