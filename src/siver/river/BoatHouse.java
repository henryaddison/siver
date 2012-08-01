package siver.river;

import java.lang.reflect.Constructor;
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
	
	public void launchBoat() {
		launchBoat(BasicBrain.class);
	}
	
	public void launchBoat(Class coxBrainClass) {
		Boat boat = new Boat(river, context, space, 0.5);
		context.add(boat);
		
		try {
			Cox cox = new Cox();
			context.add(cox);
			cox.launch(coxBrainClass, boat, getLaunchLane(), RandomHelper.nextIntFromTo(1,10));
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
