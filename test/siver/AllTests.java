package siver;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import siver.river.LandmarkTest;
import siver.river.RiverTest;

@RunWith(Suite.class)
@SuiteClasses({ LandmarkTest.class, RiverTest.class })
public class AllTests {

}
