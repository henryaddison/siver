package siver.cox;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.cox.Cox;

public class CoxObservationsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	public void testCoxObservations() {
		Boat mboat = createMock(Boat.class);
		Cox mcox = createMock(Cox.class);
		BoatNavigation mnav = createMock(BoatNavigation.class);
		new CoxObservations(mcox, mboat, mnav);
	}
	
	@Test
	public void testTravellingTooSlowly() {
		Boat mboat = createMock(Boat.class);
		Cox mcox = createMock(Cox.class);
		BoatNavigation mnav = createMock(BoatNavigation.class);
		CoxObservations obs = new CoxObservations(mcox, mboat, mnav);
		
		expect(mcox.desired_gear()).andStubReturn(5);
		replay(mcox);
		
		reset(mboat);
		expect(mboat.getGear()).andReturn(2).once();
		replay(mboat);
		assertTrue(obs.belowDesiredSpeed());
		verify(mboat);
		
		reset(mboat);
		expect(mboat.getGear()).andReturn(9).once();
		replay(mboat);
		assertFalse(obs.belowDesiredSpeed());
		verify(mboat);
	}

}
