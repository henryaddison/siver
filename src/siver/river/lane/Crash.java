package siver.river.lane;

import siver.agents.boat.CoxAgent;

public class Crash {
	private LaneEdge edge;
	
	public Crash(LaneEdge e) {
		edge = e;
	}
	
	public void reset() {
		for(CoxAgent c : edge.getCoxes()) {
			c.incapcitate();
		}
	}
	
	public LaneEdge getEdge() {
		return edge;
	}
}
