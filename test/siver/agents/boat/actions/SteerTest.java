package siver.agents.boat.actions;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import siver.context.LaneContext;
import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class SteerTest extends ActionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		setUpMocks();
		action = new Steer(mockCox);
		reset(mockCox);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() {
		Point2D.Double exp_aim_loc = new Point2D.Double(30,30);
		
		Lane mockLane = createMock(Lane.class);
		LaneNode edge_start = new LaneNode(10,30,mockLane);
		LaneNode edge_end = new LaneNode(exp_aim_loc,mockLane);
		LaneEdge<LaneNode> next_edge = new LaneEdge<LaneNode>(edge_start, edge_end);
		
		expect(mockLocation.getNode()).andReturn(edge_start).once();
		expect(mockLane.getNextEdge(edge_start, false)).andReturn(next_edge).once();
		expect(mockLocation.headingUpstream()).andReturn(false).once();
		
		mockLocation.updateEdge(next_edge);
		expectLastCall().once();
		
		expect(mockLocation.getDestinationNode()).andReturn(edge_end).once();
		
		mockBoat.steerToward(exp_aim_loc);
		expectLastCall().once();
		
		LetBoatRunTest.setUpRunAlongEdgeExpectations(mockCox, mockBoat, mockLocation);		
		
		replay(mockCox);
		replay(mockBoat);
		replay(mockLocation);
		replay(mockLane);
		
		action.execute();
		
		verify(mockCox);
		verify(mockBoat);
		verify(mockLocation);
		verify(mockLane);
		
	}

}
