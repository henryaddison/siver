package siver.agents.boat.actions;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import siver.agents.boat.*;
import siver.river.River;

public class LandTest {
	
	private BoatAgent mockBoat;
	private CoxAgent mockCox;
	private Land action;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mockBoat = createMock(BoatAgent.class);
		mockCox = createMock(CoxAgent.class);
		action = new Land(mockCox);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() {
		expect(mockCox.getBoat()).andReturn(mockBoat).once();
		mockBoat.land();
		expectLastCall().once();
		replay(mockBoat);
		replay(mockCox);
		
		action.execute();
		
		verify(mockBoat);
		verify(mockCox);
	}

}
