package siver.river.lane;

public class LaneChangeEdge<T extends LaneNode> extends LaneEdge<T> {

	public LaneChangeEdge(T source, T destination) {
		super(source, destination);
	}

}
