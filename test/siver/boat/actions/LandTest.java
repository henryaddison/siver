package siver.boat.actions;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.context.Context;
import siver.boat.actions.Land;
import siver.context.SiverContextCreator;
import siver.river.lane.LaneEdge;

public class LandTest extends ActionTest {
	
	@Override
	protected String className() {
		return Land.class.getName();
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
		Context<Object> mockContext = createMock(Context.class);
		SiverContextCreator.setContext(mockContext);
		
		LaneEdge mockEdge = createMock(LaneEdge.class);
		
		expect(mockLocation.getEdge()).andReturn(mockEdge);
		
		mockEdge.removeCox(mockCox);
		expectLastCall().once();
		
		expect(mockContext.remove(mockCox)).andReturn(true).once();
		expect(mockContext.remove(mockBoat)).andReturn(true).once();

		replay(mockContext);
		executeWithMocks();
		verify(mockContext);
	}

	

}
