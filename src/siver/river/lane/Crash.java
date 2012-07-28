package siver.river.lane;

public class Crash<T extends LaneEdge> {
	private T edge;
	
	public Crash(T e) {
		edge = e;
	}
	
	public T getEdge() {
		return edge;
	}
}
