/**
 * 
 */
package siver.river.lane;

import repast.simphony.space.graph.EdgeCreator;

/**
 * Class used by Repast's NetworkBuilder.
 * When building a network to represent a lane using LaneNodes connected by directed
 * LaneEdges and overriding the weight to be the distance between the nodes 
 * (hence we the isDirected and weight arguments are dropped on the floor by createEdge). 
 * 
 * @author henryaddison
 *
 */
public class LaneEdgeCreator<T extends LaneNode> implements EdgeCreator<LaneEdge<T>, T> {

	@Override
	public Class getEdgeType() {
		return LaneEdge.class;
	}

	@Override
	public LaneEdge<T> createEdge(T source, T target, boolean isDirected, double weight) {
		//LaneEdgeCreator is only used for directed graphs where the weight is used to determine the distance between nodes.
		return new LaneEdge<T>(source, target);
	}

}
