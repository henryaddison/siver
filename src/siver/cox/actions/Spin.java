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
	
	private LaneEdge startEdge, destinationEdge;
	private ArrayList<LaneEdge> middleEdges;
	
	private int countDown;
	
	private static final int STEPS = 60;
	
	public Spin(Cox cox) {
		super(cox);
		countDown = STEPS;
		if(location.headingUpstream()) {
			destinationLane = boat.getRiver().downstream_lane();
		} else {
			destinationLane = boat.getRiver().upstream_lane();
		}
		destinationNode = destinationLane.nodeNearest(boat.getLocation());
		
		startEdge = location.getEdge();
		destinationEdge = destinationLane.getNextEdge(destinationNode, !location.headingUpstream());
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
	}
	
	@Override
	public boolean typeSpecificExecute() {
		countDown--;
		boat.deadStop();
		
		if(countDown <= 0) {
			for(LaneEdge edge : middleEdges) {
				if(edge != destinationEdge) {
					edge.removeCox(cox);
				}
			}
			//make sure the boat is on the destination node after spinning in case the spinning animation is slightly off
			boat.moveTo(destinationNode.toNdPoint());
			location.toggleUpstream();
			location.updateEdge(destinationEdge);
			return true;
		}
		
		currentOrientation += Math.PI/STEPS;
		boat.setAngle(currentOrientation);

		
		NdPoint otherPoint = destinationNode.toNdPoint();
		double angle = SpatialMath.calcAngleFor2DMovement(boat.getSpace(), boat.getLocation(), otherPoint);
		
		boat.move(step_distance, angle);
		
		
		return false;
	}

}
