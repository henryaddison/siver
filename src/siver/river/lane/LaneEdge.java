/**
 * 
 */
package siver.river.lane;

import repast.simphony.space.graph.RepastEdge;

/**
 * A class to represent the edges in a Lane.
 * These are travelled by boats whose coxes will react when they reach each node.
 * For simplicity they are directed (going upstream get predecessor node while 
 * going downsteam get the successor node).
 * The weight is set to be the distance between nodes by default.
 *  
 * @author henryaddison
 *
 */
public class LaneEdge<T extends LaneNode> extends RepastEdge<T> {
	public LaneEdge(T source, T destination) {
		//shall assume that LaneEdges are directed by default
		super(source, destination, true);
		//weight of an edge should be the distance between the two nodes
		double w = source.distance(destination);
		setWeight(w);
	}

	public LaneNode getNextNode(boolean upstream) {
		if(upstream) {
			return getSource();
		} else {
			return getTarget();
		}
	}
}
