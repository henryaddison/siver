package siver.river;

import org.junit.Test;

import siver.river.lane.Lane;

/**
 * 
 * River is used to define a layout of the river.
 * 
 * A River object is an area defined as containing the three lanes that make it up.
 * Consider the downstream lane to be the bottom lane and upstream to be the top lane.
 * The outline of the river is then the area bounded by the points that make up the bottom boundary
 * of the downstream lane and the top boundary of the upstream lane.
 * 
 * 
 * @author henryaddison
 *
 */
public class River extends OutlinedArea {
	public class NoLaneFound extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	private Lane upstream, middle, downstream;
	
	public River(Lane u, Lane m, Lane d) {
		upstream = u;
		middle = m;
		downstream = d;
		
		top = upstream.getTop();
		bottom = downstream.getBottom();
		complete();
	}
	
	//GETTERS
	public Lane getUpstream() {
		return upstream;
	}
	
	public Lane getDownstream() {
		return downstream;
	}
	
	public Lane getMiddle() {
		return middle;
	}
	
	public Lane getLaneToLeftOf(Lane current, boolean upstream) throws NoLaneFound {
		if(!upstream) {
			if(current == downstream) return middle;
			if(current == middle) return this.upstream;
		} else {
			if(current == this.upstream) return middle;
			if(current == middle) return downstream;
		}
		throw new NoLaneFound();
	}
	
	public Lane getLaneToRightOf(Lane current, boolean upstream) throws NoLaneFound {
		return getLaneToLeftOf(current, !upstream);
	}
	
}
