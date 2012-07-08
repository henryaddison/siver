/**
 * 
 */
package siver;


import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;


import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.gis.GeographyFactoryFinder;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.SimpleCartesianAdder;
import repast.simphony.space.graph.Network;
import siver.river.River;
import siver.river.RiverFactory;
import siver.river.lane.LaneEdgeCreator;
import siver.river.lane.LaneNode;

/**
 * @author hja11
 *
 */
public class SiverContextCreator implements ContextBuilder<Object> {
	
	private static BoatHouse boatHouse;
	
	/* (non-Javadoc)
	 * @see repast.simphony.dataLoader.ContextBuilder#build(repast.simphony.context.Context)
	 */
	@Override
	public Context build(Context<Object> context) {
		context.setId("siver");
		
		int xdim = 1500;   // The x dimension of the physical space
		int ydim = 300;   // The y dimension of the physical space

		// Create a new 2D continuous space to model the physical space on which the sheep
		// and wolves will move.  The inputs to the Space Factory include the space name, 
		// the context in which to place the space, border specification,
		// random adder for populating the grid with agents,
		// and the dimensions of the grid.
		ContinuousSpace<Object> space = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null)
		.createContinuousSpace("Continuous Space", context, new SimpleCartesianAdder<Object>(),
				new repast.simphony.space.continuous.StrictBorders(), xdim, ydim);
		
		River river = RiverFactory.Test(context, space);
		
		context.add(river);
		space.moveTo(river, 0,0);
		
		BoatHouse boatHouse = new BoatHouse(new Point2D.Double(0, 30), river);
		context.add(boatHouse);
		space.moveTo(boatHouse, 0, 30);
		
		ISchedule schedule = RunEnvironment.getInstance().getCurrentSchedule();
		//Specify that the action should start at tick 1 and execute every other tick
		ScheduleParameters params = ScheduleParameters.createOneTime(1);

		//Schedule the boathouse to launch a boat on the first tick only for now
		schedule.schedule(params, boatHouse, "launch");
		
		return context;
	}
}
