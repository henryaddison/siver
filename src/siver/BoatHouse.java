package siver;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;

public class BoatHouse {
	public void launch() {
		Context<Object> context = ContextUtils.getContext(this);
		Grid<Object> grid = (Grid) context.getProjection("Simple Grid");
		ContinuousSpace<Object> space = (ContinuousSpace) context.getProjection("Continuous Space");
		NdPoint pt = space.getLocation(this);
		
		BoatAgent boat = new BoatAgent(space, grid);
		context.add(boat);
		space.moveTo(boat, pt.getX(), pt.getY());
		grid.moveTo(boat, (int)pt.getX(), (int)pt.getY());
	}
}
