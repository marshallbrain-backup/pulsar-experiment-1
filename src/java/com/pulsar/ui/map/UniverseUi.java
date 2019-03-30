package ui.map;

import ui.engine.VectorGraphics;
import universe.Universe;

public class UniverseUi {
	
	private Universe universe;
	private GalaxyUi galaxyUi;

	public UniverseUi(Universe u) {
		universe = u;
		galaxyUi = new GalaxyUi(universe.getGalaxy());
	}

	public void render(VectorGraphics vg) {
		galaxyUi.render(vg);
	}

}
