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
		LaneContext lane_context = new LaneContext("Upstream Context");
		context.addSubContext(lane_context);
		Lane up = new Lane(lane_context, "Upstream Lane");
		
		
		lane_context = new LaneContext("Middle Context");
		context.addSubContext(lane_context);
		Lane middle = new Lane(lane_context, "Middle Lane");
		
		lane_context = new LaneContext("Downstream Context");
		context.addSubContext(lane_context);
		Lane down = new Lane(lane_context, "Downstream Lane");
		
		up.start(new Point2D.Double(10, 30));
		context.add(up);
		space.moveTo(up, 10, 30);
		
		middle.start(new Point2D.Double(10, 20));
		context.add(middle);
		space.moveTo(middle, 10, 20);
		
		down.start(new Point2D.Double(10, 10));
		context.add(down);
		space.moveTo(up, 10, 10);
		
		try {
			for(int i = 1; i <= 70; i++) {
				up.extend(0);
				down.extend(0);
				middle.extend(0);
			}
			
			for(double theta = 0; theta <= PI/2.0; theta += 1/4.4) {
				up.extend(theta);
			}
			for(double theta = 0; theta <= PI/2.0; theta += 1/5.0) {
				middle.extend(theta);
			}
			for(double theta = 0; theta <= PI/2.0; theta += 1/5.5) {
				down.extend(theta);
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
				down.extend(theta);
			}
			for(double theta = PI/2.0; theta >= 0; theta -= 1/5.0) {
				middle.extend(theta);
			}
			for(double theta = PI/2.0; theta >= 0; theta -= 1/5.7) {
				up.extend(theta);
			}
			
			for(int i = 1; i <= 7; i++) {
				up.extend(0);
				middle.extend(0);
				down.extend(0);
			}
			
			for(double theta = 0; theta <= PI/3.0; theta += 1/4.5) {
				up.extend(theta);
			}
			for(double theta = 0; theta <= PI/3.0; theta += 1/5.0) {
				middle.extend(theta);
			}
			for(double theta = 0; theta <= PI/3.0; theta += 1/5.5) {
				down.extend(theta);
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
		
		return river;
	}
	
	public static River Test(Context<Object> context, ContinuousSpace<Object> space) {
		LaneContext lane_context = new LaneContext("Upstream Context");
		context.addSubContext(lane_context);
		Lane up = new Lane(lane_context, "Upstream Lane");
		
		lane_context = new LaneContext("Middle Context");
		context.addSubContext(lane_context);
		Lane middle = new Lane(lane_context, "Middle Lane");
		
		lane_context = new LaneContext("Downstream Context");
		context.addSubContext(lane_context);
		Lane down = new Lane(lane_context, "Downstream Lane");
		
		up.start(new Point2D.Double(10, 30));
		
		middle.start(new Point2D.Double(10, 20));
		
		down.start(new Point2D.Double(10, 10));
		
		
		try {
			for(int i=1; i<=10; i++) {
				
				up.extend(0);
				middle.extend(0);
				down.extend(0);
			}
			
			up.extend(PI/4.0);
			middle.extend(PI/4.0);
			down.extend(PI/4.0);
			
			up.extend(PI/2.0);
			middle.extend(PI/2.0);
			down.extend(PI/2.0);
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
		
		return river;
	}
}
