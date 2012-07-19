package siver;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import siver.agents.*;
import siver.agents.boat.*;
import siver.river.*;
import siver.river.lane.*;
import siver.agents.boat.actions.*;


@RunWith(Suite.class)
@SuiteClasses({ 
	LaneTest.class, 
	RiverTest.class, 
	LaneNodeTest.class, 
	LaneEdgeTest.class, 
	LaneEdgeCreatorTest.class, 
	BoatHouseTest.class,
	BoatAgentTest.class, 
	CoxAgentTest.class,
	CoxLocationTest.class, 
	LandTest.class,
	LetBoatRunTest.class,
	SpinTest.class, 
	SpeedUpTest.class,
	SlowDownTest.class, 
	MoveToLaneOnLeftTest.class,
	MoveToLaneOnRightTest.class})
public class AllTests {

}
