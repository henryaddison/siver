package siver.agents.boat.actions;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class MoveToLaneOnLeftTest extends ChangeLaneTest {
	@Override
	protected String className() {
		return MoveToLaneOnLeft.class.getName();
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}
	

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() {
		runExecute(river.getUpstream(), new Point2D.Double(130,30));
	}

}
