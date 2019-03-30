package ui.universe_ui;

import universe.Universe;

public class UniverseUi {
	
	private Universe universe;
	private GalaxyUi galaxyUi;

	public UniverseUi(Universe u) {
		universe = u;
		galaxyUi = new GalaxyUi(universe.getGalaxy());
	}

}
