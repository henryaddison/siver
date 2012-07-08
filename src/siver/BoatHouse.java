package siver;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.util.ContextUtils;
import siver.river.River;

public class BoatHouse {
	private River river;
	public BoatHouse(River river) {
		this.river = river;
		
		ISchedule schedule = RunEnvironment.getInstance().getCurrentSchedule();
		//Specify that the action should start at tick 1 and execute every other tick
		ScheduleParameters params = ScheduleParameters.createOneTime(1);

		//Schedule the boathouse to launch a boat on the first tick only for now
		schedule.schedule(params, this, "launch");
	}
	
	public void launch() {
		Context<Object> context = ContextUtils.getContext(this);
		ContinuousSpace<Object> space = (ContinuousSpace) context.getProjection("Continuous Space");
		NdPoint pt = space.getLocation(this);
		
		BoatAgent boat = new BoatAgent(river, space);
		context.add(boat);
		boat.launch(pt);
	}
}
