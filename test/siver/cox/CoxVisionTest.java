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
import siver.river.River;
import siver.river.lane.Lane;
import siver.river.lane.Lane.NoNextNode;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;
import siver.river.lane.LaneTest;

public class CoxVisionTest {
	private River r;
	Boat mboat;
	Cox mcox;
	BoatNavigation mnav;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		r = LaneTest.setupRiver();
		SiverContextCreator.setRiver(r);
		mboat = createMock(Boat.class);
		mcox = createMock(Cox.class);
		mnav = createMock(BoatNavigation.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCoxVision() {
		replay(mboat, mcox, mnav);
		new CoxVision(mcox, mboat, mnav);
		verify(mboat, mcox, mnav);
	}
	
	private CoxVision look() {
		expect(mnav.getLane()).andReturn(r.middle_lane()).times(6);
		expect(mnav.getDestinationNode()).andReturn(r.middle_lane().getStartNode()).times(2);
		expect(mnav.headingUpstream()).andReturn(false).times(6);
		expect(mboat.getLocation()).andReturn(new NdPoint(10,10)).times(4);
		
		replay(mboat, mcox, mnav);
		CoxVision vc = new CoxVision(mcox, mboat, mnav);
		vc.lookEverywhere();
		verify(mboat, mcox, mnav);
		reset(mboat, mcox, mnav);
		return vc;
	}
	
	@Test
	public void testLook() {
		look();
	}

	@Test
	public void testEdgesOfClearRiver() {
		CoxVision vc = look();
		assertEquals(10, vc.edgesOfClearRiver(r.middle_lane(), true));
		assertEquals(0, vc.edgesOfClearRiver(r.middle_lane(), false));
		
		assertEquals(10, vc.edgesOfClearRiver(r.upstream_lane(), true));
		assertEquals(0, vc.edgesOfClearRiver(r.upstream_lane(), false));
		
		assertEquals(10, vc.edgesOfClearRiver(r.downstream_lane(), true));
		assertEquals(0, vc.edgesOfClearRiver(r.downstream_lane(), false));
	}

	@Test
	public void testBlockedEdge() {
		CoxVision vc = look();
		
		assertNull(vc.blockedEdge(r.middle_lane(), true));
		assertNull(vc.blockedEdge(r.middle_lane(), false));
		
		assertNull(vc.blockedEdge(r.upstream_lane(), true));
		assertNull(vc.blockedEdge(r.upstream_lane(), false));
		
		assertNull(vc.blockedEdge(r.downstream_lane(), true));
		assertNull(vc.blockedEdge(r.downstream_lane(), false));
	}
	
	LaneNode mnode;
	LaneEdge medge;
	Lane mlane;
	
	@Test
	public void testNearbyBoatInfrontBoatsInfront() throws NoNextNode {
		Cox c = new Cox();
		
		LaneNode nextNode = r.middle_lane().getNextNode(r.middle_lane().getStartNode(), false);
		LaneEdge nextEdge = r.middle_lane().getNextEdge(nextNode, false);
		
		LaneNode downLaneNextNode = r.downstream_lane().getNextNode(r.downstream_lane().getStartNode(), false);
		LaneEdge downLanenextEdge = null;
		for(int i = 1; i<= 3; i++) {
			downLanenextEdge = r.downstream_lane().getNextEdge(downLaneNextNode, false);
			downLaneNextNode = downLanenextEdge.getNextNode(false);
		}
		
		nextEdge.addCox(c);
		downLanenextEdge.addCox(c);
		
		CoxVision vc = look();
		
		assertSame(nextEdge, vc.blockedEdge(r.middle_lane(), true));
		assertEquals(1, vc.edgesOfClearRiver(r.middle_lane(), true));
		
		assertSame(downLanenextEdge, vc.blockedEdge(r.downstream_lane(), true));
		assertNull(vc.blockedEdge(r.downstream_lane(), false));
		
		assertNull(vc.blockedEdge(r.upstream_lane(), true));
		assertNull(vc.blockedEdge(r.upstream_lane(), false));
		
		assertEquals(10, vc.edgesOfClearRiver(r.upstream_lane(), true));
		assertEquals(0, vc.edgesOfClearRiver(r.upstream_lane(), false));
		
		assertEquals(3, vc.edgesOfClearRiver(r.downstream_lane(), true));
		assertEquals(0, vc.edgesOfClearRiver(r.downstream_lane(), false));
	}
	
	@Test
	public void testCantSeePastBlockingNodes() throws NoNextNode {
		LaneNode nextNode = r.middle_lane().getNthNodeAhead(r.middle_lane().getStartNode(), false, 2);
		nextNode.setOpacity(1);
		CoxVision second_look = look();
		assertEquals(2, second_look.edgesOfClearRiver(r.middle_lane(), true));
	}

}
