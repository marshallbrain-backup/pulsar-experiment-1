package ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import input.Keyboard;
import input.Mouse;
import math.Other;
import ui.engine.UiElement;
import ui.engine.VectorGraphics;
import ui.engine.VectorParser;
import ui.engine.vectors.VectorLayer;
import ui.map.StarSystemUi;
import ui.view.View;
import universe.Universe;

public class Ui {
	
	private List<View> views;
	
	private Map<String, VectorLayer> vectorList;
	
	private UiElement currentUiChart;
	
	private Universe universe;
	
	public Ui(Universe u) {
		
		universe = u;
		
		vectorList = new HashMap<String, VectorLayer>();
		views = new ArrayList<View>();
		loadVectorFiles(vectorList, new File("gfx"));
		
		Map<String, VectorLayer> systemList = Other.getAllMatchingKeys(vectorList, "map\\.system\\..*", 2);
//		Map<String, VectorLayer> viewList = Other.getAllMatchingKeys(vectorList, "view\\..*", 1);
		
		currentUiChart = new StarSystemUi(systemList, universe.getGalaxy().getStarSystem(), views);
		
	}
	
	public void action(Mouse m, Keyboard k) {
		if(currentUiChart.action(m, k)) {
			return;
		}
	}

	public void render(VectorGraphics vg) {
		currentUiChart.render(vg);
	}
	
	private void loadVectorFiles(Map<String, VectorLayer> v, File file) {
		if(file.isDirectory()) {
			for(File f: file.listFiles()) {
				loadVectorFiles(v, f);
			}
		} else if(Other.getExtension(file).equals("xml")){
			String head = file.getPath().split("\\\\")[0]+"\\";
			VectorLayer vl = VectorParser.getVectors(file.getPath());
			if(vl != null) {
				v.put(file.getPath().split("\\.")[0].replace(head, "").replace("\\", "."), vl);
			}
		}
		
	}

}
