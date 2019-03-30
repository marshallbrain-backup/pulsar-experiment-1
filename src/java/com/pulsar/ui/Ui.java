package ui;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ui.engine.Vector;
import ui.engine.VectorGraphics;
import ui.engine.VectorParser;
import ui.map.Chart;
import ui.map.StarSystemUi;
import universe.Universe;

public class Ui {
	
	private Map<String, List<Vector>> vectorList;
	private Universe universe;
	private Chart currentUiChart;
	
	public Ui(Universe u) {
		
		vectorList = new HashMap<String, List<Vector>>();
		loadVectorFiles(vectorList, new File("gfx"));
		
		universe = u;
		currentUiChart = new StarSystemUi(vectorList, universe.getGalaxy().getStarSystem());
		
	}

	public void render(VectorGraphics vg) {
		currentUiChart.render(vg);
	}
	
	private void loadVectorFiles(Map<String, List<Vector>> v, File file) {
		if(file.isDirectory()) {
			for(File f: file.listFiles()) {
				loadVectorFiles(v, f);
			}
		} else {
			v.put(file.getName().split("\\.")[0], VectorParser.getVectors(file.getPath()));
		}
		
	}

}
