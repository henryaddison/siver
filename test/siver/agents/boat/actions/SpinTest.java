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
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;
import siver.river.lane.LaneTest;

public class SpinTest extends ActionTest {
	private River r;
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
		r = LaneTest.setupRiver();
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
		runExecute(1);
	}
	
	@Test
	public void testFinalExecute() {
		runExecute(9);
		
		reset(mockCox);
		reset(mockBoat);
		reset(mockLocation);
		
		mockBoat.setGear(0);
		expectLastCall().once();
		mockCox.setTickDistanceRemaining(0);
		expectLastCall().once();
		
		
		mockBoat.moveTo(new NdPoint(10,10));
		expectLastCall().once();
		
		mockLocation.toggleUpstream();
		expectLastCall().once();
		
		expect(mockLocation.headingUpstream()).andReturn(false).once();
		
		mockLocation.updateEdge(r.getDownstream().getNextEdge(r.getDownstream().getStartNode(), false));
		expectLastCall().once();
		
		
		mockCox.clearAction();
		expectLastCall().once();
		
		executeWithMocks();
	}

	
	private void runExecute(int runs) {
		mockBoat.setGear(0);
		expectLastCall().times(runs);
		
		mockCox.setTickDistanceRemaining(0);
		expectLastCall().times(runs);
		
		ContinuousSpace<Object> mockSpace = createMock(ContinuousSpace.class);
		
		expect(mockBoat.getSpace()).andStubReturn(mockSpace);
		
		
		for(int i = 1; i<= runs; i++) {
			mockBoat.setAngle(i*Math.PI/10.0);
			expectLastCall().once();
			
			NdPoint boatLoc = new NdPoint(10,30-2.0*i);
			expect(mockBoat.getLocation()).andReturn(boatLoc).once();
			
			expect(mockSpace.getDisplacement(boatLoc, new NdPoint(10,10))).andReturn(new double[]{0,-20+2.0*i}).once();
		}
		
		mockBoat.move(2.0, -Math.PI/2.0);
		expectLastCall().times(runs);
		
		replay(mockSpace);
		replay(mockLocation);
		replay(mockBoat);
		replay(mockCox);
		for(int i = 1; i<= runs; i++) {
			
			action.execute();
		}
		verify(mockCox);
		verify(mockBoat);
		verify(mockLocation);
		verify(mockSpace);
	}

	
	
}
