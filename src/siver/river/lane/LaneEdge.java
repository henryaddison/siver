/**
 * 
 */
package siver.river.lane;

import repast.simphony.space.graph.RepastEdge;

/**
 * @author henryaddison
 *
 */
public class LaneEdge<T extends LaneNode> extends RepastEdge<T> {
	public LaneEdge(T source, T destination) {
		super(source, destination, true);
		double w = source.distance(destination);
		setWeight(w);
	}

}
