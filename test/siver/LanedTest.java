package siver;

import java.awt.geom.Point2D;

import siver.context.LaneContext;
import siver.river.lane.Lane;

public class LanedTest {
	protected static Lane up, down, mid;
	
	public static void createLanes() throws Exception {
		up = new Lane(new LaneContext(), "Upstream Test Lane");
		down = new Lane(new LaneContext(), "Downstream Test Lane");
		mid = new Lane(new LaneContext(), "Middle Test Lane");
		
		up.start(new Point2D.Double(0,50));
		mid.start(new Point2D.Double(0,30));
		down.start(new Point2D.Double(0,10));
		
		up.extend(0);
		up.extend(0);
		up.extend(0);
		
		mid.extend(0);
		mid.extend(0);
		mid.extend(0);
		
		down.extend(0);
		down.extend(0);
		down.extend(0);
		
		up.complete();
		mid.complete();
		down.complete();
	}
}
