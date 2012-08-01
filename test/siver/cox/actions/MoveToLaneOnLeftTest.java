package siver.cox.actions;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.space.continuous.NdPoint;
import siver.cox.actions.MoveToLaneOnLeft;


public class MoveToLaneOnLeftTest extends ChangeLaneTest {
	@Override
	protected String className() {
		return MoveToLaneOnLeft.class.getName();
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() {
		runExecute(river.getMiddle(), river.getUpstream(), new Point2D.Double(130,30));
	}
	
	@Test
	public void testExecuteAlreadyInLeftMostLane() {
		
		expect(mockLocation.changingLane()).andReturn(false).once();
		expect(mockLocation.getLane()).andReturn(river.getUpstream());
		
		executeWithMocks();
	}
	
	@Test
	public void testExecuteNotEnoughSpaceBeforeRiverEnd() {
		NdPoint startLoc = new NdPoint(150,200);
		expect(mockBoat.getLocation()).andStubReturn(startLoc);
		expect(mockLocation.changingLane()).andReturn(false).once();
		expect(mockLocation.getLane()).andReturn(river.getMiddle());
		expect(mockLocation.getEdge()).andReturn(river.getMiddle().edgeNearest(startLoc));
		
		executeWithMocks();
	}
	
	@Test
	public void testExecuteAlreadyChangingLane() {
		expect(mockLocation.changingLane()).andReturn(true).once();
		executeWithMocks();
	}
}
