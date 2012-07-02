/**
 * 
 */
package siver.river.lane;

import repast.simphony.space.graph.EdgeCreator;

/**
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
		return new LaneEdge<T>(source, target);
	}

}
