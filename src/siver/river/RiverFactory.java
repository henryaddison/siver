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
		
		river.add(new Landmark(new Point2D.Double(10,0), new Point2D.Double(30,0)));
		river.add(new Landmark(new Point2D.Double(10,50), new Point2D.Double(30,50)));
		river.add(new Landmark(new Point2D.Double(50,90), new Point2D.Double(50,70)));
		river.add(new Landmark(new Point2D.Double(100,90), new Point2D.Double(100,70)));
		river.add(new Landmark(new Point2D.Double(120,150), new Point2D.Double(140,150)));
		river.add(new Landmark(new Point2D.Double(120,200), new Point2D.Double(140,200)));
		
		river.complete();
		return river;
	}
}
