package siver.river;

import java.util.ArrayList;

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

	private ArrayList<Lane> lanes;// upstream, middle, downstream;
	
	public River(Lane u, Lane m, Lane d) {
		lanes = new ArrayList<Lane>();
		lanes.add(u);
		lanes.add(m);
		lanes.add(d);
		
		top = upstream_lane().getTop();
		bottom = downstream_lane().getBottom();
		complete();
	}
	
	//GETTERS
	public Lane upstream_lane() {
		return lanes.get(0);
	}
	
	public Lane downstream_lane() {
		return lanes.get(2);
	}
	
	public Lane middle_lane() {
		return lanes.get(1);
	}
	
	public Lane getLaneToLeftOf(Lane current, boolean upstream) throws NoLaneFound {
		int lane_index = lanes.indexOf(current);
		if(!upstream) {
			lane_index--;
		} else {
			lane_index++;
		}
		if(lane_index < 0 || lane_index >= lanes.size()) {
			throw new NoLaneFound();
		}
		return lanes.get(lane_index);
	}
	
	public Lane getLaneToRightOf(Lane current, boolean upstream) throws NoLaneFound {
		return getLaneToLeftOf(current, !upstream);
	}
	
}
