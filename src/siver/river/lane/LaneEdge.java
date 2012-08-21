/**
 * 
 */
package siver.river.lane;

import java.util.ArrayList;

import repast.simphony.random.RandomHelper;
import repast.simphony.space.graph.RepastEdge;
import siver.context.SiverContextCreator;
import siver.cox.Cox;

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
public class LaneEdge extends RepastEdge<LaneNode> {
	
	private ArrayList<Cox> coxesOnEdge;
	
	private Crash crash;
	
	public LaneEdge(LaneNode source, LaneNode destination) {
		//shall assume that LaneEdges are directed by default
		super(source, destination, true);
		//weight of an edge should be the distance between the two nodes
		double w = source.distance(destination);
		setWeight(w);
		coxesOnEdge = new ArrayList<Cox>();
		crash = null;
	}

	public LaneNode getNextNode(boolean upstream) {
		if(upstream) {
			return getSource();
		} else {
			return getTarget();
		}
	}
	
	public synchronized void addCox(Cox cox) {
		if(!contains(cox)) {
			if(!isEmpty()) {
				if(crash == null) {
					crash = new Crash(this);
					SiverContextCreator.getContext().add(crash);
				}
			}
			coxesOnEdge.add(cox);
			if(crash != null) {
				crash.reset(cox);
			}
		}
	}
	
	public synchronized void removeCox(Cox cox) {
		if(contains(cox)) {
			coxesOnEdge.remove(cox);
			if(crash != null) {
				if(isEmpty()) {
					crash.clearUp();
				} else {
					crash.coxEscaped(cox);
				}
			}
		}
	}
	
	public boolean isEmpty() {
		return coxesOnEdge.isEmpty();
	}
	
	public boolean contains(Cox cox) {
		return coxesOnEdge.contains(cox);
	}
	
	public Crash getCrash() {
		return crash;
	}
	
	public boolean isTemporary() {
		return false;
	}
	
	public ArrayList<Cox> getCoxes() {
		return coxesOnEdge;
	}
	
	public Cox pickRandomCox() {
		if(isEmpty()) return null;
		int index = RandomHelper.nextIntFromTo(0, coxesOnEdge.size()-1);
		return coxesOnEdge.get(index);
	}
	
	public void clearCrash() {
		crash = null;
	}
}
