package siver;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import siver.agents.BoatHouseTest;
import siver.agents.boat.BoatAgentTest;
import siver.river.RiverTest;
import siver.river.lane.LaneEdgeCreatorTest;
import siver.river.lane.LaneEdgeTest;
import siver.river.lane.LaneNodeTest;
import siver.river.lane.LaneTest;

@RunWith(Suite.class)
@SuiteClasses({ LaneTest.class, RiverTest.class, 
	LaneNodeTest.class, LaneEdgeTest.class, 
	LaneEdgeCreatorTest.class, BoatHouseTest.class,
	BoatAgentTest.class})
public class AllTests {

}
