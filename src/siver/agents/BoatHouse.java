package siver.agents;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import repast.simphony.context.Context;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import siver.agents.boat.Boat;
import siver.agents.boat.Cox;
import siver.river.River;
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
		launchBoat(Cox.class.getName());
	}
	
	public void launchBoat(String coxClassName) {
		Boat boat = new Boat(river, context, space, 0.5);
		context.add(boat);
		
		try {
			Class cl = Class.forName(coxClassName);
			Constructor con = cl.getConstructor();
			Cox cox = (Cox) con.newInstance();
			context.add(cox);
			cox.launch(boat, getLaunchLane(), RandomHelper.nextIntFromTo(1,10));
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Lane getLaunchLane() {
		return river.getDownstream();
	}
}
