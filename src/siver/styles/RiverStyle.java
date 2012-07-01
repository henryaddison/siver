package siver.styles;

import java.awt.Color;
import java.awt.Font;

import repast.simphony.visualizationOGL2D.StyleOGL2D;
import saf.v3d.ShapeFactory2D;
import saf.v3d.scene.Position;
import saf.v3d.scene.VSpatial;
import siver.river.River;

public class RiverStyle implements StyleOGL2D<River> {
	
	private ShapeFactory2D shapeFactory;
	
	@Override
	public void init(ShapeFactory2D factory) {
		this.shapeFactory = factory;
	}

	@Override
	public VSpatial getVSpatial(River object, VSpatial spatial) {
		if (spatial == null) return shapeFactory.createShape(object.getOutline());
	    return spatial;
	}

	@Override
	public Color getColor(River object) {
		return Color.BLUE;
	}

	@Override
	public int getBorderSize(River object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Color getBorderColor(River object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getRotation(River object) {
		return 0;
	}

	@Override
	public float getScale(River object) {
		return 15;
	}

	@Override
	public String getLabel(River object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Font getLabelFont(River object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getLabelXOffset(River object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getLabelYOffset(River object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Position getLabelPosition(River object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getLabelColor(River object) {
		// TODO Auto-generated method stub
		return null;
	}

}
