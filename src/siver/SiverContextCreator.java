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
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.SimpleCartesianAdder;
import repast.simphony.space.gis.Geography;
import repast.simphony.space.gis.GeographyParameters;
import repast.simphony.space.gis.ShapefileLoader;
import repast.simphony.space.gis.SimpleAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.valueLayer.GridValueLayer;
import siver.river.Landmark;
import siver.river.River;
import siver.river.RiverFactory;

/**
 * @author hja11
 *
 */
public class SiverContextCreator implements ContextBuilder<Object> {
	
	private BoatHouse boatHouse;
	private River river;
	
	/* (non-Javadoc)
	 * @see repast.simphony.dataLoader.ContextBuilder#build(repast.simphony.context.Context)
	 */
	@Override
	public Context build(Context<Object> context) {
		context.setId("siver");
		
		int xdim = 201;   // The x dimension of the physical space
		int ydim = 201;   // The y dimension of the physical space

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
		
		river = RiverFactory.Test();
		context.add(river);
		space.moveTo(river, 0,0);
		grid.moveTo(river, 0,0);
		
		for(int x = 0; x<xdim; x++) {
			for(int y = 0; y<ydim;y++) {
				if(river.contains(x,y)) {
					vl.set(1, x, y);
				}
			}
		}
		
		this.boatHouse = new BoatHouse(river);
		context.add(boatHouse);
		space.moveTo(boatHouse, 15, 0);
		NdPoint pt = space.getLocation(boatHouse);
		grid.moveTo(boatHouse, (int)pt.getX(), (int)pt.getY());
		
		ISchedule schedule = RunEnvironment.getInstance().getCurrentSchedule();
		//Specify that the action should start at tick 1 and execute every other tick
		ScheduleParameters params = ScheduleParameters.createOneTime(1);

		//Schedule the boathouse to launch a boat on the first tick only for now
		schedule.schedule(params, boatHouse, "launch");
		
		
		
		Geography<Object> geoProj = GeographyFactoryFinder.createGeographyFactory(null).createGeography(
				"geo", context,
				new GeographyParameters<Object>(new SimpleAdder<Object>()));
		
		return context;
	}
	
	public BoatHouse getBoatHouse() {
		return boatHouse;
	}
	
}
