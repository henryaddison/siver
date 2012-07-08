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
import siver.river.lane.Lane;
import siver.river.lane.Lane.CompletedLaneException;
import siver.river.lane.Lane.UnstartedLaneException;
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
		LaneContext lane_context = new LaneContext();
		context.addSubContext(lane_context);
		
		Lane up = new Lane(lane_context, "Upstream Lane");
		Lane middle = new Lane(lane_context, "Middle Lane");
		Lane down = new Lane(lane_context, "Downstream Lane");
		
		up.start(new Point2D.Double(0, 50));
		context.add(up);
		space.moveTo(up, 0, 50);
		
		middle.start(new Point2D.Double(0, 30));
		context.add(middle);
		space.moveTo(middle, 0, 30);
		
		down.start(new Point2D.Double(0, 10));
		context.add(down);
		space.moveTo(up, 0, 10);
		
		for(int i = 1; i <= 10; i++) {
			try {
				up.extend(0);
				down.extend(0);
				middle.extend(0);
			} catch (UnstartedLaneException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CompletedLaneException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		River river = new River(up, middle, down);
		
		context.add(river);
		space.moveTo(river, 0,0);
		
		return river;
	}
}
