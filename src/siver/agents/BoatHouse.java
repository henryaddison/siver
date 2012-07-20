package siver.agents;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import siver.agents.boat.BoatAgent;
import siver.agents.boat.CoxAgent;
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
		BoatAgent boat = new BoatAgent(river, context, space, 0.5);
		context.add(boat);
		CoxAgent cox = new CoxAgent();
		context.add(cox);
		cox.launch(boat, getLaunchLane(), 4);
	}
	
	public Lane getLaunchLane() {
		return river.getDownstream();
	}
}
