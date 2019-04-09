package ui;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import input.Keyboard;
import input.Mouse;
import math.Other;
import ui.engine.UiElement;
import ui.engine.Vector;
import ui.engine.VectorGraphics;
import ui.engine.VectorParser;
import ui.map.StarSystemUi;
import universe.Universe;

public class Ui {
	
	private Map<String, List<Vector>> vectorList;
	
	private UiElement currentUiChart;
	
	private Universe universe;
	
	public Ui(Universe u) {
		
		universe = u;
		
		vectorList = new HashMap<String, List<Vector>>();
		loadVectorFiles(vectorList, new File("gfx"));
		
		currentUiChart = new StarSystemUi(vectorList, universe.getGalaxy().getStarSystem());
		
	}
	
	public void action(Mouse m, Keyboard k) {
		if(currentUiChart.action(m, k)) {
			return;
		}
	}

	public void render(VectorGraphics vg) {
		currentUiChart.render(vg);
	}
	
	private void loadVectorFiles(Map<String, List<Vector>> v, File file) {
		if(file.isDirectory()) {
			for(File f: file.listFiles()) {
				loadVectorFiles(v, f);
			}
		} else if(Other.getExtension(file).equals(".txt")){
			String head = file.getPath().split("\\\\")[0]+"\\";
			v.put(file.getPath().split("\\.")[0].replace(head, "").replace("\\", "."), VectorParser.getVectors(file.getPath()));
		}
		
	}

}
