package ui.universe_ui;

import ui.engine.VectorGraphics;
import universe.Galaxy;

public class GalaxyUi {
	
	private Galaxy galaxy;
	private StarSystemUi starSystemUi;

	public GalaxyUi(Galaxy g) {
		galaxy = g;
		starSystemUi = new StarSystemUi(galaxy.getStarSystem());
	}

	public void render(VectorGraphics vg) {
		starSystemUi.render(vg);
	}

}
