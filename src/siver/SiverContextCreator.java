/**
 * 
 */
package siver;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.SimpleCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
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
		
		int xdim = 50;   // The x dimension of the physical space
		int ydim = 50;   // The y dimension of the physical space

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
		
		for(int x = 10; x<20; x++) {
			for(int y = 0; y<50;y++) {
				vl.set(1, x, y);
			}
		}
		
		BoatAgent boat = new BoatAgent(space, grid);
		context.add(boat);
		space.moveTo(boat, 15, 0);
		NdPoint pt = space.getLocation(boat);
		grid.moveTo(boat, (int)pt.getX(), (int)pt.getY());
		
		return context;
	}

}
