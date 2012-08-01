package siver.river.lane;

import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.graph.RepastEdge;
import siver.boat.Cox;
import siver.river.lane.Lane.NoNextNode;

public class LaneChangeEdge extends LaneEdge {
	private Lane startLane, destinationLane;
	//need to keep track of which proper edges are currently being occupied while a boat traverses this lane
	//fortunately can only have one boat on an edge like this at a time so 
	private LaneEdge destinationLaneEdge, startLaneEdge;
	
	
	public static LaneChangeEdge createLaneChangeBranch(NdPoint startingFrom, LaneEdge sLaneEdge, boolean upstream, Lane dLane) throws NoNextNode {
		
		LaneChangeEdge return_value = null;
		
		LaneEdge dLaneEdge = dLane.edgeNearest(startingFrom);
		
		LaneNode startNode = new TemporaryLaneNode(startingFrom, dLane);
		dLane.getContext().add(startNode);
		
		LaneNode endNode;
		LaneNode sLNode;
		LaneNode dLNode;
		
		try {
			dLane.getNthNodeAhead(dLane.nodeNearest(startingFrom), upstream, 6);
			sLaneEdge.getNextNode(upstream).getLane().getNthNodeAhead(sLaneEdge.getNextNode(upstream), upstream, 6);
		} catch (NoNextNode e) {
			throw e;
		}
		
		
		for(int i = 1; i <= 5; i++) {
			sLNode = sLaneEdge.getNextNode(upstream);
			dLNode = dLaneEdge.getNextNode(upstream);
			
			
			if(i < 5) {
				double x = sLNode.getLocation().getX()*((5-i)/5.0) + dLNode.getLocation().getX()*(i/5.0);
				double y = sLNode.getLocation().getY()*((5-i)/5.0) + dLNode.getLocation().getY()*(i/5.0);
				
				endNode = new TemporaryLaneNode(x,y,dLane);
				dLane.getContext().add(endNode);
			} else {
				endNode = dLNode;
			}
			
			LaneNode source = startNode;
			LaneNode destination = endNode;
			if(upstream) {
				source = endNode;
				destination = startNode;
			}
			
			LaneChangeEdge next_edge = new LaneChangeEdge(source, destination, sLaneEdge, dLaneEdge);
			dLane.getNet().addEdge(next_edge);
			
			if(return_value == null) {
				return_value = next_edge;
			}
			
			dLaneEdge = dLane.getNextEdge(dLNode, upstream);
			sLaneEdge = sLNode.getLane().getNextEdge(sLNode, upstream);
			
			startNode = endNode;
		}
		return return_value;
	}
	
	public LaneChangeEdge(LaneNode source, LaneNode destination, LaneEdge sLaneEdge, LaneEdge dLaneEdge) {
		super(source, destination);
		this.startLaneEdge = sLaneEdge;
		this.destinationLaneEdge = dLaneEdge;
		this.destinationLane = destination.getLane();
	}
	
	@Override
	public void addCox(Cox cox) {
		startLaneEdge.addCox(cox);
		destinationLaneEdge.addCox(cox);
	}
	
	@Override
	public void removeCox(Cox cox) {
		startLaneEdge.removeCox(cox);
		destinationLaneEdge.removeCox(cox);

		
		//once the cox has left this edge then it will never be used again so we can safely remove 
		//the edge from the network and the temporary Node that we started from on this edge (i.e. the next node in the reverse direction
		destinationLane.getNet().removeEdge((RepastEdge<LaneNode>) this);
		destinationLane.getContext().remove(getNextNode(!cox.getNavigator().headingUpstream()));
		
	}
	
	@Override
	public boolean isTemporary() {
		return true;
	}
}
