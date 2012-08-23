package siver.cox;

import java.util.ArrayList;
import java.util.HashMap;

import siver.boat.Boat;
import siver.boat.BoatNavigation;
import siver.context.SiverContextCreator;
import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class CoxVision {
	private Cox cox;
	private Boat boat;
	private BoatNavigation navigator;
	private HashMap<Lane, HashMap<Boolean, HashMap<String,Object>>> vision;
	
	private static final int MAX_VIEWING_DISTANCE = 4;
	private static final String BLOCKED_EDGE_KEY = "blocked_edge";
	private static final String VISION_DISTANCE_KEY = "vision_distance";
	
	public CoxVision(Cox cox, Boat boat, BoatNavigation navigator) {
		this.cox = cox;
		this.boat = boat;
		this.navigator = navigator;
		this.vision = new HashMap<Lane, HashMap<Boolean, HashMap<String,Object>>>();
		ArrayList<Lane> all_lanes = SiverContextCreator.getRiver().getLanes();
		for(Lane lane : all_lanes) {
			this.vision.put(lane, new HashMap<Boolean, HashMap<String,Object>>());
		}
	}
	
	public static CoxVision look(Cox cox, Boat boat, BoatNavigation navigator) {
		CoxVision sight = new CoxVision(cox, boat, navigator);
		sight.look();
		
		return sight;
	}
	
	public int edgesOfClearRiver(Lane lane, boolean infront) {
		check(lane, infront);
		return (Integer) vision.get(lane).get(infront).get(VISION_DISTANCE_KEY);
	}
	
	public LaneEdge blockedEdge(Lane lane, boolean infront) {
		check(lane, infront);
		return (LaneEdge) vision.get(lane).get(infront).get(BLOCKED_EDGE_KEY);
	}
	
	private void check(Lane lane, boolean infront) {
		if(vision.get(lane) == null) throw new NullPointerException("lane not set");
		if(vision.get(lane).get(infront) == null) throw new NullPointerException("direction not set");
	}
	
	private void look() {
		for(Lane lane : vision.keySet()) {
			look(lane, true);
			look(lane, false);
		}
	}
	
	private void look(Lane lane, boolean infront) {
		LaneNode nodeToCheckFrom;
		boolean upstream = navigator.headingUpstream();
		if(lane == navigator.getLane() ) {
			//for our current lane, just check from the node ahead
			nodeToCheckFrom = navigator.getDestinationNode();
		} else {
			//for another lane, check from the start (i.e. next node in reverse direction) of the nearest edge (so this will include the nearest edge)
			nodeToCheckFrom = lane.edgeNearest(boat.getLocation()).getNextNode(!upstream);
		}
		HashMap<String,Object> sees = new HashMap<String,Object>();
		vision.get(lane).put(infront, sees);
		
		Integer edges_ahead = 0;
		LaneNode node = nodeToCheckFrom;
		
		sees.put(BLOCKED_EDGE_KEY, null);
		sees.put(VISION_DISTANCE_KEY, edges_ahead);
		while(edges_ahead < MAX_VIEWING_DISTANCE) {
			LaneEdge edge = node.getLane().getNextEdge(node, (infront == upstream));
			//if there is no further edge then there no blocked edge infront but viewing distance is restricted (because we're at the end of the river)
			if(edge == null) {
				return;
			}
			
			//as soon as we find an empty edge in front we can set the blocked edge to it and return
			if(!edge.isEmpty()) {
				sees.put(BLOCKED_EDGE_KEY, edge);
				return;
			}
			//otherwise move on to next edge
			node = edge.getNextNode((infront == upstream));
			edges_ahead++;
			sees.put(VISION_DISTANCE_KEY, edges_ahead);
		}
		return;
	}
}
