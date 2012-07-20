package siver.agents.boat.actions;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MoveToLaneOnRightTest extends ChangeLaneTest {
	@Override
	protected String className() {
		return MoveToLaneOnRight.class.getName();
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
		runExecute(river.getMiddle(), river.getDownstream(), new Point2D.Double(130,10));
	}
}
