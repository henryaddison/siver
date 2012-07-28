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
import siver.river.lane.Crash.CrashError;

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
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReset() {
		Crash c = new Crash(edge);
		setupCrash(c);
		assertEquals(10, c.ticksUntilRelease());
		
	}
	
	public void setupCrash(Crash c) {
		expect(edge.getCoxes()).andStubReturn(coxes);
		replay(edge);
		for(CoxAgent mockc : coxes) {
			mockc.incapcitate();
			expectLastCall().once();
		}
		replay(coxes.toArray());
		c.reset();
		verify(coxes.toArray());
		
		reset(coxes.toArray());
		reset(edge);
	}
	
	@Test
	public void testStepMidway() {
		Crash c = new Crash(edge);
		setupCrash(c);
		assertEquals(10, c.ticksUntilRelease());
		c.step();
		assertEquals(9, c.ticksUntilRelease());
	}
	
	private void completeCrash(Crash c, boolean repeat) {
		for(int i = 10; i>1; i--) {
			c.step();
		}
		assertEquals(1, c.ticksUntilRelease());
		
		CoxAgent toBeChosen = coxes.get(1);
		if(!repeat) {
			expect(edge.pickRandomCox()).andReturn(toBeChosen).once();
		}
		replay(edge);
		
		toBeChosen.recapcitate();
		expectLastCall().once();
		
		replay(coxes.toArray());
		c.step();
		verify(coxes.toArray());
		verify(edge);
		assertSame(toBeChosen, c.getChosenCox());
		
		reset(coxes.toArray());
		reset(edge);
	}
	
	@Test
	public void testStepEndNoCoxChosen() {
		Crash c = new Crash(edge);
		setupCrash(c);
		completeCrash(c, false);
		assertEquals(0, c.ticksUntilRelease());
		c.step();
		assertEquals(0, c.ticksUntilRelease());
	}
	
	@Test
	public void testStepEndCoxChosenAlready() {
		Crash c = new Crash(edge);
		setupCrash(c);
		completeCrash(c, false);
		setupCrash(c);
		completeCrash(c, true);
		
		CoxAgent chosen = coxes.get(1);
		chosen.recapcitate();
		expectLastCall().once();
		c.step();
	}
	
	
	@Test
	public void testCoxEscaped() {
		Crash c = new Crash(edge);
		setupCrash(c);
		completeCrash(c, false);
		
		expect(edge.pickRandomCox()).andReturn(coxes.get(0)).once();
		coxes.get(0).recapcitate();
		expectLastCall().once();
		
		replay(edge);
		replay(coxes.toArray());
		
		c.coxEscaped(coxes.get(1));
		
		verify(edge);
		verify(coxes.toArray());
	}
}
