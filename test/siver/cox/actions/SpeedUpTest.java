package siver.cox.actions;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import siver.cox.actions.SpeedUp;

public class SpeedUpTest extends ActionTest {

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
		mockBoat.shiftUp();
		expectLastCall().once();
		
		executeWithMocks();
	}

	@Override
	protected String className() {
		return SpeedUp.class.getName();
	}

}
