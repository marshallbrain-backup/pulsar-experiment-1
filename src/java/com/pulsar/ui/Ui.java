package ui;

import ui.engine.VectorGraphics;
import ui.universe_ui.UniverseUi;
import universe.Universe;

public class Ui {
	
	private Universe universe;
	private UniverseUi universeUi;
	
	public Ui(Universe u) {
		universe = u;
		universeUi = new UniverseUi(universe);
	}

	public void render(VectorGraphics vg) {
		universeUi.render(vg);
	}

}
