package siver.river;

import java.awt.geom.Point2D;

/**
 * Factory for making complete River objects.
 * 
 * @author henryaddison
 *
 */
public class RiverFactory {
	/**
	 * Creates a simple, made-up river with a couple of corners.
	 * 
	 * @return completed River object based on the test coordinates
	 */
	public static River Test() {
		River river = new River();
		
		river.add(0,0);
		river.add(1000, 0);
		river.add(1000, 250);
		river.add(1500, 250);
		river.add(1500, 280);
		river.add(970, 280);
		river.add(970,30);
		river.add(0,30);
		
		river.complete();
		
		return river;
	}
}
