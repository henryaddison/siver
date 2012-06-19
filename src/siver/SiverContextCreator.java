/**
 * 
 */
package siver;


import java.awt.Polygon;
import java.util.ArrayList;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.SimpleCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.valueLayer.GridValueLayer;

/**
 * @author hja11
 *
 */
public class SiverContextCreator implements ContextBuilder<Object> {
	
	/* (non-Javadoc)
	 * @see repast.simphony.dataLoader.ContextBuilder#build(repast.simphony.context.Context)
	 */
	@Override
	public Context build(Context<Object> context) {
		context.setId("siver");
		
		int xdim = 101;   // The x dimension of the physical space
		int ydim = 101;   // The y dimension of the physical space

		// Create a new 2D grid to model the discrete patches of grass.  The inputs to the
		// GridFactory include the grid name, the context in which to place the grid,
		// and the grid parameters.  Grid parameters include the border specification,
		// random adder for populating the grid with agents, boolean for multiple occupancy,
		// and the dimensions of the grid.
		Grid<Object> grid = GridFactoryFinder.createGridFactory(null).createGrid("Simple Grid", context,
				new GridBuilderParameters<Object>(new repast.simphony.space.grid.StrictBorders(),
						new SimpleGridAdder<Object>(), true, xdim, ydim));

		// Create a new 2D continuous space to model the physical space on which the sheep
		// and wolves will move.  The inputs to the Space Factory include the space name, 
		// the context in which to place the space, border specification,
		// random adder for populating the grid with agents,
		// and the dimensions of the grid.
		ContinuousSpace<Object> space = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null)
		.createContinuousSpace("Continuous Space", context, new SimpleCartesianAdder<Object>(),
				new repast.simphony.space.continuous.StrictBorders(), xdim, ydim);
		
		// Create a new 2D value layer to store the state of the river grid.  This is
		// only used for visualization since it's faster to draw the value layer
		// in 2D displays compared with rendering each grass patch as an agent.
		GridValueLayer vl = new GridValueLayer("River Field", true, 
				new repast.simphony.space.grid.StrictBorders(),xdim,ydim);
		
		context.addValueLayer(vl);
		
		River river = new River();
		context.add(river);
		
		river.add(new GridPoint(10,0), new GridPoint(30,0));
		river.add(new GridPoint(10,50), new GridPoint(30,50));
		river.add(new GridPoint(50,90), new GridPoint(50,70));
		river.add(new GridPoint(100,90), new GridPoint(100,70));
		
		for(int x = 0; x<xdim; x++) {
			for(int y = 0; y<ydim;y++) {
				if(river.contains(x,y)) {
					vl.set(1, x, y);
				}
			}
		}
		
		BoatHouse boatHouse = new BoatHouse();
		context.add(boatHouse);
		space.moveTo(boatHouse, 15, 0);
		NdPoint pt = space.getLocation(boatHouse);
		grid.moveTo(boatHouse, (int)pt.getX(), (int)pt.getY());
		
		ISchedule schedule = RunEnvironment.getInstance().getCurrentSchedule();
		//Specify that the action should start at tick 1 and execute every other tick
		ScheduleParameters params = ScheduleParameters.createOneTime(1);

		//Schedule my agent to execute the move method given the specified schedule parameters.&nbsp;
		schedule.schedule(params, boatHouse, "launch");
		
		return context;
	}

}
