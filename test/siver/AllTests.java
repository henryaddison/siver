package siver;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import siver.river.RiverTest;
import siver.river.lane.LaneNodeTest;
import siver.river.lane.LaneTest;

@RunWith(Suite.class)
@SuiteClasses({ LaneTest.class, RiverTest.class, LaneNodeTest.class })
public class AllTests {

}
