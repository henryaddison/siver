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
	LaneChangeEdgeTest.class,
	LaneEdgeCreatorTest.class, 
	BoatHouseTest.class,
	BoatTest.class, 
	CoxTest.class,
	BoatNavigationTest.class, 
	LandTest.class,
	LetBoatRunTest.class,
	SpinTest.class, 
	SpeedUpTest.class,
	SlowDownTest.class, 
	MoveToLaneOnLeftTest.class,
	MoveToLaneOnRightTest.class,})
public class AllTests {

}
