package siver.agents.boat.actions;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import siver.river.River;
import siver.river.lane.LaneTest;

public class SpinTest extends ActionTest {
	@Override
	protected String className() {
		return Spin.class.getName();
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Override
	protected void preNewActionSetup() {
		River r = LaneTest.setupRiver();
		expect(mockBoat.getRiver()).andStubReturn(r);
		expect(mockBoat.getLocation()).andReturn(new NdPoint(10,30)).times(2);
		expect(mockBoat.getAngle()).andReturn(0.0).once();
		expect(mockLocation.headingUpstream()).andReturn(true).once();
	}
	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() {
		mockBoat.setGear(0);
		expectLastCall().once();
		
		mockCox.setTickDistanceRemaining(0);
		expectLastCall().once();
		
		mockBoat.setAngle(Math.PI/10.0);
		
		ContinuousSpace<Object> mockSpace = createMock(ContinuousSpace.class);
		
		expect(mockBoat.getSpace()).andStubReturn(mockSpace);
		
		NdPoint boatLoc = new NdPoint(10,30);
		expect(mockBoat.getLocation()).andReturn(boatLoc).once();
		
		expect(mockSpace.getDisplacement(boatLoc, new NdPoint(10,10))).andReturn(new double[]{0,-20});
		
		mockBoat.move(2.0, -Math.PI/2.0);
		expectLastCall().once();
		
		replay(mockSpace);
		executeWithMocks();
		verify(mockSpace);
	}


	
	
}
