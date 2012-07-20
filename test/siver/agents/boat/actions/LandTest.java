package siver.agents.boat.actions;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
		mockBoat.land();
		expectLastCall().once();
		executeWithMocks();
	}

	

}
