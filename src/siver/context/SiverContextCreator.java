/**
 * 
 */
package siver.context;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.SimpleCartesianAdder;
import repast.simphony.ui.RSApplication;
import siver.experiments.InprogressExperiment;
import siver.river.BoatHouse;
import siver.river.River;
import siver.river.RiverFactory;
import siver.ui.UserPanel;

/**
 * @author hja11
 *
 */
public class SiverContextCreator implements ContextBuilder<Object> {
	
	private static Context<Object> mainContext;
	public static Context<Object> getContext() {
		return mainContext;
	}
	public static void setContext(Context<Object> context) {
		mainContext = context;
	}
	private static ContinuousSpace<Object> space;
	public static ContinuousSpace<Object> getSpace() {
		return space;
	}
	public static void setSpace(ContinuousSpace<Object> s) {
		space = s;
	}
	
	public static River river;
	public static River getRiver() {
		return river;
	}
	public static void setRiver(River r) {
		river = r;
	}
	
	private static final Integer EXPERIMENT_ID = null;
	
	/* (non-Javadoc)
	 * @see repast.simphony.dataLoader.ContextBuilder#build(repast.simphony.context.Context)
	 */
	@Override
	public Context build(Context<Object> context) {
		context.setId("siver");
		
		int xdim = 2200;   // The x dimension of the physical space
		int ydim = 1000;   // The y dimension of the physical space
		
		space = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null)
		.createContinuousSpace("Continuous Space", context, new SimpleCartesianAdder<Object>(),
				new repast.simphony.space.continuous.StrictBorders(), xdim, ydim);
		
		river = RiverFactory.Cam(context, space);
		
		BoatHouse boatHouse = new BoatHouse(river, context, space);
		context.add(boatHouse);
		space.moveTo(boatHouse, 0, 20);
		
		initializeExperiment();
		
		if(!InprogressExperiment.instance().isAutomated()) {
			RSApplication.getRSApplicationInstance().removeCustomUserPanel();
			RSApplication.getRSApplicationInstance().addCustomUserPanel(new UserPanel(boatHouse));
		}
		
		mainContext = context;
		
		return context;
	}
	
	public void initializeExperiment() {
		InprogressExperiment.start(EXPERIMENT_ID);
		//also schedule a method to run when the simulation ends
		ISchedule schedule = RunEnvironment.getInstance().getCurrentSchedule();
		ScheduleParameters params = ScheduleParameters.createOneTime(ScheduleParameters.END);
		schedule.schedule(params, this, "endExperiment");
	}
	
	public void endExperiment() {
		InprogressExperiment.end();
	}
}
