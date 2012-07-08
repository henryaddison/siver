package siver.river;

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
 * @see java.awt.geom.Path2D
 * @see siver.river.Landmark
 * 
 * @author henryaddison
 *
 */
public class River extends OutlinedArea {
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
	
}
