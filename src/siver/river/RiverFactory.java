package siver.river;

import java.awt.geom.Point2D;

import repast.simphony.context.Context;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.space.continuous.ContinuousSpace;
import siver.BoatHouse;
import siver.LaneContext;
import siver.river.lane.LaneEdgeCreator;
import siver.river.lane.LaneNode;

/**
 * Factory for making complete River objects.
 * 
 * @author henryaddison
 *
 */
public class RiverFactory {
	/**
	 * Creates a simple, made-up river with a couple of corners.
	 * 
	 * @return completed River object based on the test coordinates
	 */
	public static River Test(Context<Object> context, ContinuousSpace<Object> space) {
		River river = new River();
		
		Context<LaneNode> lane_context = new LaneContext();
		context.addSubContext(lane_context);
		
		context.add(river);
		space.moveTo(river, 0,0);
		
		BoatHouse boatHouse = new BoatHouse(new Point2D.Double(0, 15), river);
		context.add(boatHouse);
		space.moveTo(boatHouse, 0, 15);
		
		ISchedule schedule = RunEnvironment.getInstance().getCurrentSchedule();
		//Specify that the action should start at tick 1 and execute every other tick
		ScheduleParameters params = ScheduleParameters.createOneTime(1);

		//Schedule the boathouse to launch a boat on the first tick only for now
		schedule.schedule(params, boatHouse, "launch");
		
		river.add(0,0);
		river.add(1000, 0);
		river.add(1000, 250);
		river.add(1500, 250);
		river.add(1500, 280);
		river.add(970, 280);
		river.add(970,30);
		river.add(0,30);
		
		river.complete();
		
		NetworkBuilder<LaneNode> builder = new NetworkBuilder<LaneNode>("Middle Lane",
				lane_context, true);
		builder.setEdgeCreator(new LaneEdgeCreator<LaneNode>());
		middleLaneNetwork = builder.buildNetwork();
		
		builder = new NetworkBuilder<LaneNode>("Downstream Lane",
				lane_context, true);
		builder.setEdgeCreator(new LaneEdgeCreator<LaneNode>());
		downstreamLaneNetwork = builder.buildNetwork();
		
		builder = new NetworkBuilder<LaneNode>("Upstream Lane",
				lane_context, true);
		builder.setEdgeCreator(new LaneEdgeCreator<LaneNode>());
		upstreamLaneNetwork = builder.buildNetwork();
		
		LaneNode current = boatHouse;
		
		for(int i = 10; i <= 985; i+=10) {
			LaneNode next = new LaneNode(i,15);
			middleLaneNetwork.addEdge(current, next, next.distance(current));
			current = next;
		}
		
		for(int i = 15; i <= 265; i+=10) {
			LaneNode next = new LaneNode(985, i);
			middleLaneNetwork.addEdge(current, next, next.distance(current));
			current = next;
		}
		
		for(int i = 985; i< 1500; i+=10) {
			LaneNode next = new LaneNode(i, 265);
			middleLaneNetwork.addEdge(current, next, next.distance(current));
			current = next;
		}
		
		return river;
	}
}
