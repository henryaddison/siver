package siver.styles;

import java.awt.Color;
import java.awt.Font;

import repast.simphony.visualizationOGL2D.StyleOGL2D;
import saf.v3d.ShapeFactory2D;
import saf.v3d.scene.Position;
import saf.v3d.scene.VSpatial;
import siver.BoatHouse;

public class BoatHouseStyle implements StyleOGL2D<BoatHouse> {
	
	private ShapeFactory2D shapeFactory;
	
	@Override
	public void init(ShapeFactory2D factory) {
		this.shapeFactory = factory;
		
	}

	@Override
	public VSpatial getVSpatial(BoatHouse object, VSpatial spatial) {
		if (spatial == null) return shapeFactory.createRectangle(20, 60, false);
	    return spatial;
	}

	@Override
	public Color getColor(BoatHouse object) {
		return Color.BLACK;
	}

	@Override
	public int getBorderSize(BoatHouse object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Color getBorderColor(BoatHouse object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getRotation(BoatHouse object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getScale(BoatHouse object) {
		return 15;
	}

	@Override
	public String getLabel(BoatHouse object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Font getLabelFont(BoatHouse object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getLabelXOffset(BoatHouse object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getLabelYOffset(BoatHouse object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Position getLabelPosition(BoatHouse object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getLabelColor(BoatHouse object) {
		// TODO Auto-generated method stub
		return null;
	}

}
