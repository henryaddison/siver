package siver.river.lane;

import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.graph.RepastEdge;
import siver.cox.Cox;
import siver.river.lane.Lane.NoNextNode;

public class LaneChangeEdge extends LaneEdge {
	final private static int NODES_AHEAD_TO_JOIN_LANE = 4;
	private Lane destinationLane;
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
		
		
		for(int i = 1; i <= NODES_AHEAD_TO_JOIN_LANE; i++) {
			sLNode = sLaneEdge.getNextNode(upstream);
			dLNode = dLaneEdge.getNextNode(upstream);
			
			
			if(i < NODES_AHEAD_TO_JOIN_LANE) {
				double x = sLNode.getLocation().getX()*((NODES_AHEAD_TO_JOIN_LANE-i)/((double)NODES_AHEAD_TO_JOIN_LANE)) + dLNode.getLocation().getX()*(i/((double)NODES_AHEAD_TO_JOIN_LANE));
				double y = sLNode.getLocation().getY()*((NODES_AHEAD_TO_JOIN_LANE-i)/((double)NODES_AHEAD_TO_JOIN_LANE)) + dLNode.getLocation().getY()*(i/((double)NODES_AHEAD_TO_JOIN_LANE));
				
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
