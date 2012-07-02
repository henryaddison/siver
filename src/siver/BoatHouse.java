package siver;

import java.awt.geom.Point2D;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;
import siver.river.River;
import siver.river.lane.LaneNode;

public class BoatHouse extends LaneNode {
	private River river;
	public BoatHouse(Point2D.Double l, River river) {
		super(l);
		this.river = river;
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
