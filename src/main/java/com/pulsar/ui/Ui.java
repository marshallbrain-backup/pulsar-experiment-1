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
import ui.engine.XmlParser;
import ui.engine.scripts.ScriptGroup;
import ui.engine.vectors.Circle;
import ui.engine.vectors.LinkVector;
import ui.engine.vectors.Rectangle;
import ui.engine.vectors.Text;
import ui.engine.vectors.TextRegion;
import ui.engine.vectors.Vector;
import ui.engine.vectors.VectorGroup;
import ui.map.StarSystemUi;
import ui.view.View;
import universe.Universe;

public class Ui {
	
	private List<View> views;
	
	private Map<String, VectorGroup> vectorList;
	private Map<String, ScriptGroup> scriptList;
	
	private UiElement currentUiChart;
	
	private Universe universe;
	
	public Ui(Universe u) {
		
		universe = u;
		
		vectorList = new HashMap<String, VectorGroup>();
		scriptList = new HashMap<String, ScriptGroup>();
		views = new ArrayList<View>();
		
//		View.initGroups(vectorList, actionList);
		
		loadFiles(vectorList, scriptList, new File("gfx"));
		
		Map<String, VectorGroup> systemVectors = Other.getAllMatchingKeys(vectorList, "map\\.system\\..*", 2);
		
		currentUiChart = new StarSystemUi(systemVectors, universe.getGalaxy().getStarSystem());
		
	}
	
	public void action(Mouse m, Keyboard k) {
		if(currentUiChart.action(m, k)) {
			return;
		}
	}

	public void render(VectorGraphics vg) {
		
		currentUiChart.render(vg);
		
		for(View v: views) {
			v.render(vg);
		}
		
	}
	
	private void loadFiles(Map<String, VectorGroup> vl, Map<String, ScriptGroup> sl, File file) {
		if(file.isDirectory()) {
			for(File f: file.listFiles()) {
				loadFiles(vl, sl, f);
			}
		} else if(Other.getExtension(file).equals("xml")){
			loadVectors(vl, file);
		} else if(Other.getExtension(file).equals("txt")){
			loadScripts(sl, file);
		}
		
	}
	
	private void loadScripts(Map<String, ScriptGroup> sl, File file) {
		
		ScriptGroup sg = new ScriptGroup(file);
		
		if(!sg.isEmpty()) {
			String head = file.getPath().split("\\\\")[0]+"\\";
			sl.put(file.getPath().split("\\.")[0].replace(head, "").replace("\\", "."), sg);
		}
		
	}

	private void loadVectors(Map<String, VectorGroup> vl, File file) {
		
		Class<?>[] classList = {
				VectorGroup.class, 
				Circle.class, Rectangle.class, LinkVector.class, TextRegion.class, Text.class
				};
		
		Object o = XmlParser.getXml(file.getPath(), classList);
		
		if(o == null)
			return;
		
		VectorGroup vg = (VectorGroup) o;
		
		if(vg != null && !vg.getVectors().isEmpty()) {
			try {
				for(Vector v: vg.getVectors()) {
					v.setStyle();
				}
			} catch(ClassCastException e) {
				vg = null;
			}
		} else {
			vg = null;
		}
		
		if(vg != null) {
			String head = file.getPath().split("\\\\")[0]+"\\";
			vl.put(file.getPath().split("\\.")[0].replace(head, "").replace("\\", "."), vg);
		}
		
	}

}
