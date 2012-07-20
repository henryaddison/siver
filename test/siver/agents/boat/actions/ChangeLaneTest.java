package siver.agents.boat.actions;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.lang.reflect.Constructor;

import org.easymock.Capture;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import siver.agents.boat.CoxAgent;
import siver.context.LaneContext;
import siver.river.River;
import siver.river.RiverFactory;
import siver.river.lane.Lane;
import siver.river.lane.LaneChangeEdge;
import siver.river.lane.LaneNode;

public abstract class ChangeLaneTest extends ActionTest {
	protected River river;
	
	protected abstract String className();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Context<Object> context = createMock(Context.class);
		ContinuousSpace<Object> space = createMock(ContinuousSpace.class);
		
		context.addSubContext(anyObject(LaneContext.class));
		expectLastCall().times(3);
		expect(context.add(anyObject(River.class))).andReturn(true).once();
		expect(space.moveTo(anyObject(River.class), eq(0.0),eq(0.0))).andReturn(true).once();
		replay(context);
		replay(space);
		river = RiverFactory.Test(context, space);
		verify(space);
		verify(context);
		
		reset(space);
		reset(context);
		
		setUpMocks();
		
		Class cl = Class.forName(className());
		Constructor con = cl.getConstructor(CoxAgent.class);
		action = (ChangeLane) con.newInstance(mockCox);
		
		verify(mockCox);
		reset(mockCox);
		
		expect(mockBoat.getRiver()).andStubReturn(river);
		expect(mockLocation.headingUpstream()).andStubReturn(false);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	protected void executeWithMocks() {
		replay(mockLocation);
		replay(mockBoat);
		replay(mockCox);
		action.execute();
		verify(mockCox);
		verify(mockBoat);
		verify(mockLocation);
	}
	
	protected void runExecute(Lane startingLane, Lane expDestLane, Point2D.Double expDestLocation) {
		expect(mockBoat.getLocation()).andStubReturn(new NdPoint(15,20));
		expect(mockLocation.changingLane()).andReturn(false).once();
		expect(mockLocation.getLane()).andReturn(startingLane);
		
		Capture<LaneChangeEdge> captured = new Capture<LaneChangeEdge>();
		
		mockLocation.updateEdge(capture(captured), eq(false));
		expectLastCall().once();
		
		executeWithMocks();
		
		LaneChangeEdge newEdge = captured.getValue();
		LaneNode srcNode = (LaneNode) newEdge.getSource();
		assertTrue(srcNode.isTemporary());
		assertEquals(river.getMiddle(), ((ChangeLane) action).getStartLane());
		assertEquals(expDestLane, ((ChangeLane) action).getTargetLane());
		
	}
}
