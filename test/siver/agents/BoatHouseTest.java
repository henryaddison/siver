package siver.agents;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import siver.LanedTest;
import siver.river.River;
import siver.river.lane.Lane.UnstartedLaneException;

public class BoatHouseTest extends LanedTest {

	private static River r;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		createLanes();
		r = new River(up, mid, down);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBoatHouse() {
		BoatHouse bh = new BoatHouse(r);
	}

	@Test
	public void testLaunchBoat() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetLaunchNode() throws UnstartedLaneException {
		BoatHouse bh = new BoatHouse(r);
		assertEquals(down.getStartNode(), bh.getLaunchNode());
	}

}
