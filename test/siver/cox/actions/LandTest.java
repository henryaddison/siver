package siver.cox.actions;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.context.Context;
import siver.context.SiverContextCreator;
import siver.cox.actions.Land;
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
		mockCox.land();
		expectLastCall().once();
		executeWithMocks();
	}

	

}
