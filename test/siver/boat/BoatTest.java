package siver.boat;

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
import siver.boat.Boat;
import siver.context.SiverContextCreator;
import siver.cox.Cox;
import siver.river.River;

public class BoatTest {

	private Context<Object> mockContext;
	private ContinuousSpace<Object> mockSpace;
	private River mockRiver;
	private Boat boat;
	private Cox mockCox;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mockContext = EasyMock.createMock(Context.class);
		SiverContextCreator.setContext(mockContext);
		mockSpace = EasyMock.createMock(ContinuousSpace.class);
		SiverContextCreator.setSpace(mockSpace);
		mockRiver = EasyMock.createMock(River.class);
		mockCox = EasyMock.createMock(Cox.class);
		boat = new Boat(mockRiver, mockContext, mockSpace, 0.5);
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
		assertTrue(boat instanceof Boat);
		verify(mockContext);
		verify(mockSpace);
		verify(mockRiver);
	}
	
	private void launchBoat() {
		expect(mockSpace.moveTo(boat, 0,0)).andReturn(true).times(1);
		replay(mockContext);
		replay(mockSpace);
		boat.launch(mockCox, new Point2D.Double(0,0));
		verify(mockContext);
		verify(mockSpace);
	}
	
	@Test
	public void testLaunch() {		
		launchBoat();
		assertEquals(0,boat.getAngle(), 1E-5);
		assertEquals(0,boat.getSpeed(), 1E-5);
	}

	@Test
	public void testMove() {
		double exp_distance = 3;
		double exp_angle = Math.PI/2.0;
		NdPoint expNewLoc = new NdPoint(10,5);
		boat.setAngle(exp_angle);
		expect(mockSpace.moveByVector(boat, exp_distance,exp_angle,0)).andReturn(expNewLoc).once();
		replay(mockSpace);
		boat.move(exp_distance);
		verify(mockSpace);
	}

	@Test
	public void testGetAndSetAngle() {
		boat.setAngle(Math.PI);
		assertEquals(Math.PI, boat.getAngle(), 1E-5);
		
		boat.setAngle(1);
		assertEquals(1, boat.getAngle(), 1E-5);
	}

	@Test
	public void testGetSpeed() {
		boat.setGear(3);
		assertEquals(1.5, boat.getSpeed(), 1E-5);
		
		boat.setGear(5);
		assertEquals(2.5, boat.getSpeed(), 1E-5);
	}
	
	@Test
	public void testSetAndGetGear() {
		boat.setGear(3);
		assertEquals(3, boat.getGear());
		
		boat.setGear(5);
		assertEquals(5, boat.getGear());
		
		boat.setGear(-1);
		assertEquals(0, boat.getGear());
		
		boat.setGear(11);
		assertEquals(10, boat.getGear());
	}
	
	@Test
	public void testShiftGear() {
		boat.setGear(3);
		assertEquals(3, boat.getGear());
		boat.shiftUp();
		assertEquals(4, boat.getGear());
		
		boat.shiftDown();
		assertEquals(3, boat.getGear());
		
		boat.setGear(0);
		assertEquals(0, boat.getGear());
		boat.shiftDown();
		assertEquals(0, boat.getGear());
		boat.shiftUp();
		assertEquals(1, boat.getGear());
		
		
		boat.setGear(10);
		assertEquals(10, boat.getGear());
		boat.shiftUp();
		assertEquals(10, boat.getGear());
		boat.shiftDown();
		assertEquals(9, boat.getGear());
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
	public void testSteerToward() {
		launchBoat();
		reset(mockSpace);
		expect(mockSpace.getLocation(boat)).andReturn(new NdPoint(0,0)).once();
		expect(mockSpace.getDisplacement(new NdPoint(0,0), new NdPoint(0,100))).andReturn(new double[]{0,100}).once();
		replay(mockSpace);
		boat.steerToward(new Point2D.Double(0,100));
		verify(mockSpace);
		assertEquals(Math.PI/2.0,boat.getAngle(), 1E-5);
	}
	
	@Test
	public void testDeadStop() {
		launchBoat();
		boat.setGear(5);
		replay(mockCox);
		boat.deadStop();
		verify(mockCox);
		assertEquals(0,boat.getGear());
		assertEquals(0,boat.getSpeed(), 1E-5);
		assertEquals(0,boat.getTickDistanceRemaining(), 1E-5);
	}
	
	@Test
	public void testGetAndSetTickDistanceRemaining() {
		launchBoat();
		assertEquals(0,boat.getTickDistanceRemaining(), 1E-5);
		boat.setTickDistanceRemaining(15.0);
		assertEquals(15,boat.getTickDistanceRemaining(), 1E-5);
	}

}