package ui.universe_ui;

import universe.Galaxy;

public class GalaxyUi {
	
	private Galaxy galaxy;
	private StarSystemUi starSystemUi;

	public GalaxyUi(Galaxy g) {
		galaxy = g;
		starSystemUi = new StarSystemUi(galaxy.getStarSystem());
	}

}
