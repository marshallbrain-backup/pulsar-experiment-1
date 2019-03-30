package ui;

import ui.engine.VectorGraphics;
import ui.map.Chart;
import ui.map.StarSystemUi;
import universe.Universe;

public class Ui {
	
	private Universe universe;
	private Chart currentUiChart;
	
	public Ui(Universe u) {
		universe = u;
		currentUiChart = new StarSystemUi(universe.getGalaxy().getStarSystem());
	}

	public void render(VectorGraphics vg) {
		currentUiChart.render(vg);
	}

}
