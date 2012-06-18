package siver;

import repast.simphony.engine.schedule.ScheduledMethod;

public class BoatAgent {
	// Schedule the step method for agents.  The method is scheduled starting at 
	// tick one with an interval of 1 tick.  Specifically, the step starts at 1, and
	// and recurs at 2,3,4,...etc
	@ScheduledMethod(start = 1, interval = 1, shuffle=true)
	public void step() {
		// Override by subclasses
	}
}
