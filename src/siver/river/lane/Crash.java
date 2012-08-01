package siver.river.lane;

import repast.simphony.engine.schedule.ScheduledMethod;
import siver.context.SiverContextCreator;
import siver.cox.Cox;

public class Crash {
	public class CrashError extends RuntimeException {
		public CrashError(String msg) {
			super(msg);
		}
	}

	//the edge the crash belongs to
	private LaneEdge edge;
	//how many more ticks before a boat gets recapitated
	private int holdUpCountDown;
	//the boat chosen to be allowed to move
	private Cox chosenCox;
	
	private final static int HOLD_UP = 10;
	
	public Crash(LaneEdge e) {
		edge = e;
	}
	
	public void reset() {
		holdUpCountDown = HOLD_UP;
		for(Cox c : edge.getCoxes()) {
			c.incapcitate();
		}
	}
	
	public LaneEdge getEdge() {
		return edge;
	}
	
	@ScheduledMethod(start = 1, interval = 1, priority=100)
	public void step() {
		//only do anything if the count down is currently > 0
		if(inProgress()) {
			holdUpCountDown--;
			//then if it hits 0 this go, make sure to recapcitate a cox
			if(holdUpComplete()) {
				recapcitateCox();
			}
		} 
	}
	
	public int ticksUntilRelease() {
		return holdUpCountDown;
	}
	
	public Cox getChosenCox() {
		return chosenCox;
	}
	
	private void recapcitateCox() {
		if(chosenCox == null) chosenCox = edge.pickRandomCox();
		chosenCox.recapcitate();
	}
	
	private boolean inProgress() {
		return holdUpCountDown > 0;
	}
	
	private boolean holdUpComplete() {
		return !inProgress();
	}
	
	public void coxEscaped(Cox cox) {
		if(cox == chosenCox) {
			chosenCox = null;
			recapcitateCox();
		}
	}
	
	public void clearUp() {
		//all coxes should be recapcitated
		for(Cox c : edge.getCoxes()) {
			if(c.isIncapcitated()) throw new CrashError("trying to finish with a crash before all coxes have been recapcitated");
		}
		//can now safely remove this from the context
		SiverContextCreator.getContext().remove(this);
		//and wipe the crash from the edge
		edge.clearCrash();
	}
}
