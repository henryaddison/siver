package siver.river.lane;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import siver.agents.boat.CoxAgent;

public class CrashTest {
	private LaneEdge edge;
	private ArrayList<CoxAgent> coxes;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		edge = createMock(LaneEdge.class);
		coxes = new ArrayList<CoxAgent>();
		for(int i=0;i<2;i++) {
			coxes.add(createMock(CoxAgent.class));
			coxes.add(createMock(CoxAgent.class));
		}
		expect(edge.getCoxes()).andStubReturn(coxes);
		replay(edge);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReset() {
		Crash c = new Crash(edge);
		
		for(CoxAgent mockc : coxes) {
			mockc.incapcitate();
			expectLastCall().once();
		}
		replay(coxes.toArray());
		c.reset();
		verify(coxes.toArray());
	}

}
