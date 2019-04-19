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
import ui.engine.actions.Action;
import ui.engine.actions.ActionGroup;
import ui.engine.actions.Click;
import ui.engine.actions.Open;
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
	private Map<String, ActionGroup> actionList;
	
	private UiElement currentUiChart;
	
	private Universe universe;
	private ActionHandler actionHandler;
	
	public Ui(Universe u) {
		
		universe = u;
		
		vectorList = new HashMap<String, VectorGroup>();
		actionList = new HashMap<String, ActionGroup>();
		views = new ArrayList<View>();
		
		View.initGroups(vectorList, actionList);
		actionHandler = new ActionHandler(actionList, views);
		
		loadVectorFiles(vectorList, new File("gfx"));
		loadActionFiles(actionList, new File("action"));
		
		Map<String, VectorGroup> systemVectors = Other.getAllMatchingKeys(vectorList, "map\\.system\\..*", 2);
		Map<String, ActionGroup> systemActions = Other.getAllMatchingKeys(actionList, "map\\.system\\..*", 2);
		
		currentUiChart = new StarSystemUi(systemVectors, systemActions, actionHandler, universe.getGalaxy().getStarSystem());
		
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
	
	private void loadVectorFiles(Map<String, VectorGroup> vl, File file) {
		if(file.isDirectory()) {
			for(File f: file.listFiles()) {
				loadVectorFiles(vl, f);
			}
		} else if(Other.getExtension(file).equals("xml")){
			String head = file.getPath().split("\\\\")[0]+"\\";
			
			Class<?>[] classList = {
					VectorGroup.class, 
					Circle.class, Rectangle.class, LinkVector.class, TextRegion.class, Text.class
					};
			
			Object o = XmlParser.getXml(file.getPath(), classList);
			
			if(o == null)
				return;
			
			VectorGroup vg = (VectorGroup) o;
			vg.propegateParameters();
			
			if(vg != null && !vg.getVectors().isEmpty()) {
				try {
					for(Vector v: vg.getVectors()) {
						v.setStyle(Other.convertStyle(v.getStyleString()));
					}
				} catch(ClassCastException e) {
					vg = null;
				}
			} else {
				vg = null;
			}
			
			if(vg != null) {
				vl.put(file.getPath().split("\\.")[0].replace(head, "").replace("\\", "."), vg);
			}
		}
		
	}
	
	private void loadActionFiles(Map<String, ActionGroup> al, File file) {
		
		if(file.isDirectory()) {
			for(File f: file.listFiles()) {
				loadActionFiles(al, f);
			}
		} else if(Other.getExtension(file).equals("xml")){
			
			String head = file.getPath().split("\\\\")[0]+"\\";
			
			Class<?>[] classList = {ActionGroup.class, Click.class, Open.class};
			
			Object o = XmlParser.getXml(file.getPath(), classList);
			
			if(o == null)
				return;
						
			ActionGroup ag = (ActionGroup) o;
			ag.propegateParameters();
			
			if(!ag.getActions().isEmpty() && ag.getActions().get(0) instanceof Action) {
			} else {
				ag = null;
			}
			
			if(ag != null) {
				al.put(file.getPath().split("\\.")[0].replace(head, "").replace("\\", "."), ag);
			}
			
		}
		
	}

}
