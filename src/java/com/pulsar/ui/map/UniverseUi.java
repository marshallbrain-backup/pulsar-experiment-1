package ui.map;

import ui.engine.UiElement;
import ui.engine.VectorGraphics;
import universe.Universe;

public class UniverseUi implements UiElement {
	
	private Universe universe;

	public UniverseUi(Universe u) {
		universe = u;
	}
	
	@Override
	public void render(VectorGraphics g) {
	}

}
