package siver.agents;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.util.ContextUtils;
import siver.agents.boat.BoatAgent;
import siver.river.River;
import siver.river.lane.Lane.UnstartedLaneException;
import siver.river.lane.LaneNode;

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
	
	public BoatHouse(River river) {
		this.river = river;
	}
	
	public BoatAgent launchBoat() {
		Context<Object> context = ContextUtils.getContext(this);
		ContinuousSpace<Object> space = (ContinuousSpace) context.getProjection("Continuous Space");
		
		BoatAgent boat = new BoatAgent(river, space);
		context.add(boat);
		boat.launch(getLaunchNode());
		return boat;
	}
	
	public LaneNode getLaunchNode() {
		try {
			return river.getDownstream().getStartNode();
		} catch (UnstartedLaneException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
