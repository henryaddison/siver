package siver.styles;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Path2D;

import repast.simphony.visualizationOGL2D.StyleOGL2D;
import saf.v3d.ShapeFactory2D;
import saf.v3d.scene.Position;
import saf.v3d.scene.VSpatial;
import siver.boat.Boat;

public class BoatStyle implements StyleOGL2D<Boat> {

	private ShapeFactory2D shapeFactory;
	
	@Override
	public void init(ShapeFactory2D factory) {
		this.shapeFactory = factory;
	}
	
	@Override
	public VSpatial getVSpatial(Boat object, VSpatial spatial) {
		Path2D.Double boatOutline = new Path2D.Double();
		boatOutline.moveTo(0, 8.5);
		
		boatOutline.lineTo(-1, 5);
		boatOutline.lineTo(-3.5, 5);
		boatOutline.lineTo(-3.5, 3.5);
		boatOutline.lineTo(-1, 3.5);
		boatOutline.lineTo(-1, 2);
		boatOutline.lineTo(-3.5, 2);
		boatOutline.lineTo(-3.5, 0.5);
		boatOutline.lineTo(-1, 0.5);
		boatOutline.lineTo(-1, -1);
		boatOutline.lineTo(-3.5, -1);
		boatOutline.lineTo(-3.5, -2.5);
		boatOutline.lineTo(-1, -2.5);
		boatOutline.lineTo(-1, -4);
		boatOutline.lineTo(-3.5, -4);
		boatOutline.lineTo(-3.5, -5.5);
		boatOutline.lineTo(-1, -5.5);
		boatOutline.lineTo(-1, -8.5);
		boatOutline.lineTo(1, -8.5);
		boatOutline.lineTo(1, -5.5);
		boatOutline.lineTo(3.5, -5.5);
		boatOutline.lineTo(3.5, -4);
		boatOutline.lineTo(1, -4);
		boatOutline.lineTo(1, -2.5);
		boatOutline.lineTo(3.5, -2.5);
		boatOutline.lineTo(3.5, -1);
		boatOutline.lineTo(1, -1);
		boatOutline.lineTo(1, 0.5);
		boatOutline.lineTo(3.5, 0.5);
		boatOutline.lineTo(3.5, 2);
		boatOutline.lineTo(1, 2);
		boatOutline.lineTo(1, 3.5);
		boatOutline.lineTo(3.5, 3.5);
		boatOutline.lineTo(3.5, 5);
		boatOutline.lineTo(1, 5);
		
		boatOutline.closePath();
		
		if (spatial == null) return shapeFactory.createShape(boatOutline);
	    return spatial;
	}

	@Override
	public Color getColor(Boat object) {
//		if(object.onRiver()) {
//			return Color.RED;
//		} else {
//			return Color.BLACK;
//		}
		return Color.RED;
	}

	@Override
	public int getBorderSize(Boat object) {
		return 0;
	}

	@Override
	public Color getBorderColor(Boat object) {
		return null;
	}

	@Override
	public float getRotation(Boat object) {
		double angle = object.getAngle();
		float angleForView = (float)-(angle*180.0/Math.PI - 90.0);
		return angleForView;
	}

	@Override
	public float getScale(Boat object) {
		return 15;
	}

	@Override
	public String getLabel(Boat object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Font getLabelFont(Boat object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getLabelXOffset(Boat object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getLabelYOffset(Boat object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Position getLabelPosition(Boat object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getLabelColor(Boat object) {
		// TODO Auto-generated method stub
		return null;
	}

}
