package ui.map;

import ui.engine.UiElement;
import ui.engine.VectorGraphics;
import universe.Galaxy;

public class GalaxyUi implements UiElement {
	
	private Galaxy galaxy;

	public GalaxyUi(Galaxy g) {
		galaxy = g;
	}
	
	@Override
	public void render(VectorGraphics g) {
	}

}
