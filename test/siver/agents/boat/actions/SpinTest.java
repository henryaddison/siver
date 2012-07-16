package siver.agents.boat.actions;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.reset;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SpinTest extends ActionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		setUpMocks();
		action = new Spin(mockCox);
		reset(mockCox);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() {
		fail("Not yet implemented");
	}
}
