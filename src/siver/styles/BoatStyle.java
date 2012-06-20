package siver.styles;

import java.awt.Color;
import java.awt.Font;

import repast.simphony.visualizationOGL2D.StyleOGL2D;
import saf.v3d.ShapeFactory2D;
import saf.v3d.scene.Position;
import saf.v3d.scene.VSpatial;
import siver.BoatAgent;

public class BoatStyle implements StyleOGL2D<BoatAgent> {

	private ShapeFactory2D shapeFactory;
	private BoatAgent boat;
	
	@Override
	public void init(ShapeFactory2D factory) {
		this.shapeFactory = factory;
	}

	@Override
	public VSpatial getVSpatial(BoatAgent object, VSpatial spatial) { 
		if (spatial == null) return shapeFactory.createRectangle(7,17);
	    return spatial;
	}

	@Override
	public Color getColor(BoatAgent object) {
		return Color.RED;
	}

	@Override
	public int getBorderSize(BoatAgent object) {
		return 0;
	}

	@Override
	public Color getBorderColor(BoatAgent object) {
		return null;
	}

	@Override
	public float getRotation(BoatAgent object) {
		double angle = object.getAngle();
		float angleForView = (float)-(angle*180.0/Math.PI - 90.0);
		return angleForView;
	}

	@Override
	public float getScale(BoatAgent object) {
		return 15;
	}

	@Override
	public String getLabel(BoatAgent object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Font getLabelFont(BoatAgent object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getLabelXOffset(BoatAgent object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getLabelYOffset(BoatAgent object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Position getLabelPosition(BoatAgent object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getLabelColor(BoatAgent object) {
		// TODO Auto-generated method stub
		return null;
	}

}