package siver.agents.boat.actions;

import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.NdPoint;
import siver.agents.boat.CoxAgent;
import siver.river.lane.Lane;
import siver.river.lane.LaneEdge;
import siver.river.lane.LaneNode;

public class Spin extends Action {
	private double startOrientation, currentOrientation, step_distance;
	private Lane destinationLane;
	private LaneNode destinationNode;
	
	private int countDown;
	
	private static final int STEPS = 10;
	
	public Spin(CoxAgent cox) {
		super(cox);
		countDown = 10;
		if(location.headingUpstream()) {
			destinationLane = boat.getRiver().getDownstream();
		} else {
			destinationLane = boat.getRiver().getUpstream();
		}
		destinationNode = destinationLane.nodeNearest(boat.getLocation());
		startOrientation = cox.getBoat().getAngle();
		currentOrientation = startOrientation;
		
		step_distance = destinationNode.distance(boat.getLocation())/STEPS;
	}
	
	@Override
	public boolean typeSpecificExecute() {
		countDown--;
		boat.setGear(0);
		cox.setTickDistanceRemaining(0);
		
		if(countDown <= 0) {
			//make sure the boat is on the destination node after spinning in case the spinning animation is slightly off
			boat.moveTo(destinationNode.toNdPoint());
			location.toggleUpstream();
			LaneEdge new_edge = destinationLane.getNextEdge(destinationNode, location.headingUpstream());
			location.updateEdge(new_edge);
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
