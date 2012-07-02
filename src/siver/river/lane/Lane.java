package siver.river.lane;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Lane {
	ArrayList<Point2D.Double> top, bottom, mid;
	
	
	final private static double width = 10;
	final private static double edge_length = 20;
	
	public Lane(Point2D.Double start) {
		top = new ArrayList<Point2D.Double>();
		bottom = new ArrayList<Point2D.Double>();
		mid = new ArrayList<Point2D.Double>();
		
		bottom.add(new Point2D.Double(start.getX(), start.getY() - width ));
		top.add(new Point2D.Double(start.getX(), start.getY() + width ));
		mid.add(new Point2D.Double(start.getX(), start.getY() ));
	}
	
	public void add(double heading) {
		int lasti = mid.size()-1;
		Point2D.Double lastp = mid.get(lasti);
		
		AffineTransform at = new AffineTransform();
		at.translate(lastp.getX(), lastp.getY());
		at.rotate(heading);
		
		Point2D.Double next_top = new Point2D.Double();
		Point2D.Double next_mid = new Point2D.Double();
		Point2D.Double next_bottom = new Point2D.Double();
		
		at.transform(new Point2D.Double(edge_length, width), next_top);
		at.transform(new Point2D.Double(edge_length, 0), next_mid);
		at.transform(new Point2D.Double(edge_length, -width), next_bottom);
		
		top.add(next_top);
		mid.add(next_mid);
		bottom.add(next_bottom);
		
	}
	
	public ArrayList<Point2D.Double> getTop() {
		return top;
	}
	
	public ArrayList<Point2D.Double> getMid() {
		return mid;
	}
	
	public ArrayList<Point2D.Double> getBottom() {
		return bottom;
	}
}
