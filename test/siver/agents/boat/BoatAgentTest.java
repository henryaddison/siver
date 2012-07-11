package siver.agents.boat;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import siver.river.River;

public class BoatAgentTest {

	private Context<Object> mockContext;
	private ContinuousSpace<Object> mockSpace;
	private River mockRiver;
	private BoatAgent boat;
	private CoxAgent mockCox;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mockContext = EasyMock.createMock(Context.class);
		mockSpace = EasyMock.createMock(ContinuousSpace.class);
		mockRiver = EasyMock.createMock(River.class);
		mockCox = EasyMock.createMock(CoxAgent.class);
		boat = new BoatAgent(mockRiver, mockContext, mockSpace);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBoatAgent() {
		replay(mockContext);
		replay(mockSpace);
		replay(mockRiver);
		assertNotNull(boat);
		assertTrue(boat instanceof BoatAgent);
		verify(mockContext);
		verify(mockSpace);
		verify(mockRiver);
	}

	@Test
	public void testLaunch() {
		expect(mockSpace.moveTo(boat, 0,0)).andReturn(true).times(1);
		expect(mockContext.add(anyObject(BoatCorner.class))).andReturn(true).times(4);
		replay(mockContext);
		replay(mockSpace);
		replay(mockRiver);
		replay(mockCox);
		
		boat.launch(mockCox, new Point2D.Double(0,0));
		
		assertEquals(0,boat.getAngle(), 1E-5);
		assertEquals(4,boat.getSpeed(), 1E-5);
		
		verify(mockContext);
		verify(mockSpace);
		verify(mockRiver);
		verify(mockCox);
	}

	@Test
	public void testLand() {
		expect(mockContext.remove(null)).andReturn(true).once();
		expect(mockContext.remove(boat)).andReturn(true).once();
		replay(mockContext);
		boat.land();
		verify(mockContext);
	}

	@Test
	public void testMove() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAndSetAngle() {
		boat.setAngle(Math.PI);
		assertEquals(Math.PI, boat.getAngle(), 1E-5);
		
		boat.setAngle(1);
		assertEquals(1, boat.getAngle(), 1E-5);
	}

	@Test
	public void testSetAndGetSpeed() {
		boat.setSpeed(3);
		assertEquals(3, boat.getSpeed(), 1E-5);
		
		boat.setSpeed(5);
		assertEquals(5, boat.getSpeed(), 1E-5);
	}
	
	@Test
	public void testGetLocation() {
		NdPoint expLoc = new NdPoint(10,5);
		expect(mockSpace.getLocation(boat)).andReturn(expLoc);
		replay(mockSpace);
		assertEquals(expLoc, boat.getLocation());
		verify(mockSpace);
	}

	@Test
	public void testGetSpace() {
		assertEquals(mockSpace, boat.getSpace());
	}

	@Test
	public void testGetRiver() {
		assertEquals(mockRiver, boat.getRiver());
	}

	@Test
	public void testOnRiver() {
		fail("Not yet implemented");
	}

}
