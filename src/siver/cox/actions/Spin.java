package siver.cox.actions;

import java.util.ArrayList;

import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.NdPoint;
import siver.cox.Cox;
import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class Spin extends Action {
	private double startOrientation, currentOrientation, step_distance;
	private Lane destinationLane;
	private LaneNode destinationNode;
	
	private LaneEdge destinationEdge;
	private ArrayList<LaneEdge> middleEdges;
	
	private int countDown;
	
	private static final int STEPS = 60;
	
	private boolean initialized = false;
	
	public Spin(Cox cox) {
		super(cox);
	}
	
	@Override
	public boolean typeSpecificExecute() {
		if(!initialized) {
			setupSpin();
			//if for some reason we couldn't set up the spin properly (no edge to spin too), then do nothing
			if(!initialized) {
				return true;
			}
		}
		countDown--;
		boat.deadStop();
		
		if(countDown <= 0) {
			completeSpin();
			return true;
		}
		
		rotateAndMoveBoat();
		return false;
	}
	
	private void setupSpin() {
		countDown = STEPS;
		if(location.headingUpstream()) {
			destinationLane = boat.getRiver().downstream_lane();
		} else {
			destinationLane = boat.getRiver().upstream_lane();
		}
		destinationNode = destinationLane.nodeNearest(boat.getLocation());
		
		destinationEdge = destinationLane.getNextEdge(destinationNode, !location.headingUpstream());
		if(destinationEdge == null) {
			return;
		}
		destinationEdge.addCox(cox);
		
		middleEdges = new ArrayList<LaneEdge>();
		for(Lane lane : boat.getRiver().getLanes()) {
			LaneEdge edge = lane.edgeNearest(boat.getLocation());  
			middleEdges.add(edge);
			lane.edgeNearest(boat.getLocation()).addCox(cox);
		}
		startOrientation = cox.getBoat().getAngle();
		currentOrientation = startOrientation;
		
		step_distance = destinationNode.distance(boat.getLocation())/STEPS;
		initialized = true;
	}
	
	private void completeSpin() {
		for(LaneEdge edge : middleEdges) {
			if(edge != destinationEdge) {
				edge.removeCox(cox);
			}
		}
		//make sure the boat is on the destination node after spinning in case the spinning animation is slightly off
		boat.moveTo(destinationNode.toNdPoint());
		location.toggleUpstream();
		location.updateEdge(destinationEdge);
	}
	
	private void rotateAndMoveBoat() {
		currentOrientation += Math.PI/STEPS;
		boat.setAngle(currentOrientation);

		
		NdPoint otherPoint = destinationNode.toNdPoint();
		double angle = SpatialMath.calcAngleFor2DMovement(boat.getSpace(), boat.getLocation(), otherPoint);
		
		boat.move(step_distance, angle);
	}

}
