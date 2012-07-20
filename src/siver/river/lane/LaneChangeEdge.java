package siver.river.lane;

import repast.simphony.space.graph.RepastEdge;
import siver.agents.boat.CoxAgent;

public class LaneChangeEdge<T extends LaneNode> extends LaneEdge<T> {
	private Lane startLane, destinationLane;
	//need to keep track of which proper edges are currently being occupied while a boat traverses this lane
	//fortunately can only have one boat on an edge like this at a time so 
	private LaneEdge<LaneNode> occupyingInDestinationLane, occupyingInStartLane;
	
	public LaneChangeEdge(T source, T destination, Lane sLane) {
		super(source, destination);
		this.startLane = sLane;
		this.destinationLane = source.getLane();
	}
	
	@Override
	public void addCox(CoxAgent cox) {
		occupyingInDestinationLane = destinationLane.edgeNearest(cox.getBoat().getLocation());
		occupyingInDestinationLane.addCox(cox);
	}
	
	@Override
	public void removeCox(CoxAgent cox) {
		occupyingInDestinationLane.removeCox(cox);
		occupyingInStartLane.removeCox(cox);
		
		destinationLane.getNet().removeEdge((RepastEdge<LaneNode>) this);
		
		LaneNode endPoint;
		endPoint = this.getSource();
		if(endPoint.isTemporary())	destinationLane.getContext().remove(endPoint);
		endPoint = this.getTarget();
		if(endPoint.isTemporary())	destinationLane.getContext().remove(endPoint);
	}
	
	
	//when a boat moves along an edge like this we need to keep track of which real edges are actually being occupied by this boat
	//we use this call back to update the edges occupied by this cox
	//since there's only one boat on this 
	@Override
	public void coxMoved(CoxAgent cox) {
		LaneEdge<LaneNode> newOccupyingInDestinationLane = destinationLane.edgeNearest(cox.getBoat().getLocation());
		if(newOccupyingInDestinationLane != occupyingInDestinationLane) {
			occupyingInDestinationLane.removeCox(cox);
			newOccupyingInDestinationLane.addCox(cox);
			occupyingInDestinationLane = newOccupyingInDestinationLane;
		}
		
		LaneEdge<LaneNode> newOccupyingInStartLane = startLane.edgeNearest(cox.getBoat().getLocation());
		if(newOccupyingInStartLane != occupyingInStartLane) {
			occupyingInStartLane.removeCox(cox);
			newOccupyingInStartLane.addCox(cox);
			occupyingInStartLane = newOccupyingInStartLane;
		}
		
		
	}
}
