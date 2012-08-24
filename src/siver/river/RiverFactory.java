package siver.river;

import static java.lang.Math.PI;
import java.awt.geom.Point2D;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import siver.context.LaneContext;
import siver.river.lane.Lane;
import siver.river.lane.Lane.CompletedLaneException;
import siver.river.lane.Lane.UnstartedLaneException;

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
	public static River Cam(Context<Object> context, ContinuousSpace<Object> space) {
		Lane up = createLane("Upstream", context, space, new Point2D.Double(10, 30));
		Lane middle = createLane("Middle", context, space, new Point2D.Double(10, 20));
		Lane down = createLane("Downstream", context, space, new Point2D.Double(10, 10));
		
		try {
			for(int i = 1; i <= 70; i++) {
				up.extend(0);
				down.extend(0);
				middle.extend(0);
			}
			
			for(double theta = 0; theta <= PI/2.0; theta += 1/4.4) {
				up.extend(theta, 3*Lane.DEFAULT_OPACITY);
			}
			for(double theta = 0; theta <= PI/2.0; theta += 1/5.0) {
				middle.extend(theta, 3*Lane.DEFAULT_OPACITY);
			}
			for(double theta = 0; theta <= PI/2.0; theta += 1/5.5) {
				down.extend(theta, 3*Lane.DEFAULT_OPACITY);
			}
			
			up.extend(PI/2.0);
			middle.extend(PI/2.0);
			down.extend(PI/2.0);
			
			for(int i = 1; i<= 12; i++) {
				up.extend(PI/2.0);
				middle.extend(PI/2.0);
				down.extend(PI/2.0);
			}
			
			for(double theta = PI/2.0; theta >= 0 ; theta -= 1/4.4) {
				down.extend(theta, 3*Lane.DEFAULT_OPACITY);
			}
			for(double theta = PI/2.0; theta >= 0; theta -= 1/5.0) {
				middle.extend(theta, 3*Lane.DEFAULT_OPACITY);
			}
			for(double theta = PI/2.0; theta >= 0; theta -= 1/5.7) {
				up.extend(theta, 3*Lane.DEFAULT_OPACITY);
			}
			
			for(int i = 1; i <= 7; i++) {
				up.extend(0);
				middle.extend(0);
				down.extend(0);
			}
			
			for(double theta = 0; theta <= PI/3.0; theta += 1/4.5) {
				up.extend(theta, 3*Lane.DEFAULT_OPACITY);
			}
			for(double theta = 0; theta <= PI/3.0; theta += 1/5.0) {
				middle.extend(theta, 3*Lane.DEFAULT_OPACITY);
			}
			for(double theta = 0; theta <= PI/3.0; theta += 1/5.5) {
				down.extend(theta, 3*Lane.DEFAULT_OPACITY);
			}
			
			for(int i = 1; i <= 25; i++) {
				up.extend(PI/3.0);
				middle.extend(PI/3.0);
				down.extend(PI/3.0);
			}
			
		} catch (UnstartedLaneException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompletedLaneException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		River river = new River(up, middle, down);
		
		context.add(river);
		space.moveTo(river, 0,0);
		
		BoatHouse boatHouse = new BoatHouse(river, context, space);
		context.add(boatHouse);
		space.moveTo(boatHouse, 0, 20);
		river.setBoathouse(boatHouse);
		
		return river;
	}
	
	
	
	public static River Test(Context<Object> context, ContinuousSpace<Object> space) {
		Lane up = createLane("Upstream", context, space, new Point2D.Double(10, 30));
		Lane middle = createLane("Middle", context, space, new Point2D.Double(10, 20));
		Lane down = createLane("Downstream", context, space, new Point2D.Double(10, 10));
		
		try {
			for(int i=1; i<=10; i++) {
				up.extend(0);
				middle.extend(0);
				down.extend(0);
			}
			
			up.extend(PI/4.0, 3*Lane.DEFAULT_OPACITY);
			middle.extend(PI/4.0, 3*Lane.DEFAULT_OPACITY);
			down.extend(PI/4.0, 3*Lane.DEFAULT_OPACITY);
			
			up.extend(PI/2.0, 3*Lane.DEFAULT_OPACITY);
			middle.extend(PI/2.0, 3*Lane.DEFAULT_OPACITY);
			down.extend(PI/2.0, 3*Lane.DEFAULT_OPACITY);
		} catch (UnstartedLaneException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompletedLaneException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		River river = new River(up, middle, down);
		
		context.add(river);
		space.moveTo(river, 0,0);
		
		BoatHouse boatHouse = new BoatHouse(river, context, space);
		context.add(boatHouse);
		space.moveTo(boatHouse, 0, 20);
		river.setBoathouse(boatHouse);
		
		return river;
	}
	
	private static Lane createLane(String name, Context<Object> context, ContinuousSpace<Object> space, Point2D.Double start_point) {
		LaneContext lane_context = new LaneContext(name+" Context");
		context.addSubContext(lane_context);
		Lane lane = new Lane(lane_context, name+" Lane");
		context.add(lane);
		
		lane.start(start_point);
		space.moveTo(lane, start_point.getX(), start_point.getY());
		
		return lane;
	}
}
