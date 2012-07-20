package siver.river.lane;

import siver.agents.boat.CoxAgent;

public class LaneChangeEdge<T extends LaneNode> extends LaneEdge<T> {
	private Lane startLane, destinationLane;
	private LaneEdge<LaneNode> occupyingInStartLane;
	private LaneEdge<LaneNode> occupyingInDestinationLane;
	
	public LaneChangeEdge(T source, T destination, Lane sLane) {
		super(source, destination);
		this.startLane = sLane;
		this.destinationLane = source.getLane();
	}
	
	@Override
	public void addCox(CoxAgent cox) {
		occupyingInStartLane = cox.getLocation().getEdge();
		occupyingInDestinationLane = destinationLane.edgeNearest(cox.getBoat().getLocation());
		occupyingInDestinationLane.addCox(cox);
	}
	
	@Override
	public void removeCox(CoxAgent cox) {
		occupyingInStartLane.removeCox(cox);
		occupyingInDestinationLane.removeCox(cox);
	}
	
	
	@Override
	public void coxMoved(CoxAgent cox) {
		
	}
}
