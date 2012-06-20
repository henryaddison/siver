package siver.styles;

import java.awt.Color;

import repast.simphony.valueLayer.ValueLayer;
import repast.simphony.visualizationOGL2D.ValueLayerStyleOGL;

public class RiverStyle implements ValueLayerStyleOGL {
	private ValueLayer layer;
	
	@Override
	public Color getColor(double... coordinates) {
		double v = layer.get(coordinates);
		if(v > 0) {
			return Color.BLUE;
		}
		else {
			return Color.GREEN;
		}
	}

	@Override
	public float getCellSize() {
		return 15.0f;
	}

	@Override
	public void init(ValueLayer layer) {
		this.layer = layer;
	}

}
