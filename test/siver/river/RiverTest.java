package siver.river;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import siver.LaneContext;
import siver.river.River;
import siver.river.lane.Lane;

public class RiverTest {
	private static Lane up, down, mid;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		up = new Lane(new LaneContext(), "Upstream Test Lane");
		down = new Lane(new LaneContext(), "Downstream Test Lane");
		mid = new Lane(new LaneContext(), "Middle Test Lane");
		
		up.start(new Point2D.Double(0,50));
		mid.start(new Point2D.Double(0,30));
		down.start(new Point2D.Double(0,10));
		
		up.extend(0);
		up.extend(0);
		up.extend(0);
		
		mid.extend(0);
		mid.extend(0);
		mid.extend(0);
		
		down.extend(0);
		down.extend(0);
		down.extend(0);
		
		up.complete();
		mid.complete();
		down.complete();
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
	public void testRiver() {
		River r = new River(up,mid,down);
		assertTrue(r.contains(10,10));
		assertTrue(r.contains(10,20));
		assertFalse(r.contains(10,70));
		assertFalse(r.contains(70,10));
	}
	
	@Test
	public void testLaneGetters() {
		River r = new River(up,mid,down);
		assertEquals(up, r.getUpstream());
		assertEquals(mid, r.getMiddle());
		assertEquals(down, r.getDownstream());
	}

}
