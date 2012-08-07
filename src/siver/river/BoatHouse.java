package siver.river;

import java.lang.reflect.InvocationTargetException;

import repast.simphony.context.Context;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import siver.boat.Boat;
import siver.cox.Cox;
import siver.cox.brains.BasicBrain;
import siver.river.lane.Lane;

/**
 * A BoatHouse is where boats are launched from and then return to home.
 * 
 * A BoatHouse is on a River. It launches boats onto a node in a lane defined to be the launch node.
 * For now the launch node will be the first node on the downstream lane.
 * 
 * @author henryaddison
 *
 */

public class BoatHouse {
	private River river;
	private Context<Object> context;
	private ContinuousSpace<Object> space;
	
	public BoatHouse(River river, Context<Object> context, ContinuousSpace<Object> space) {
		this.river = river;
		this.context = context;
		this.space = space;
	}
	
	public void manualLaunch() {
		manualLaunch(BasicBrain.class);
	}
	
	public void automaticLaunch(Integer scheduled_launch_id, Integer desired_gear, Double speed_multiplier, Double distance_to_cover, String coxBrainClassName) {
		Boat boat = new Boat(river, context, space, speed_multiplier);
		context.add(boat);
		
		Cox cox = new Cox();
		context.add(cox);
		
		if(!coxBrainClassName.startsWith("siver.")) {
			coxBrainClassName = BasicBrain.class.getPackage().getName()+"."+coxBrainClassName;
		}
		
		try {
			cox.launch(Class.forName(coxBrainClassName), boat, getLaunchLane(), desired_gear, speed_multiplier, distance_to_cover, scheduled_launch_id);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void manualLaunch(Class coxBrainClass) {
		Boat boat = new Boat(river, context, space, 0.5);
		context.add(boat);
		
		try {
			Cox cox = new Cox();
			context.add(cox);
			cox.launch(coxBrainClass, boat, getLaunchLane(), RandomHelper.nextIntFromTo(1,10), 0.5, 4000, null);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public Lane getLaunchLane() {
		return river.getDownstream();
	}
}
