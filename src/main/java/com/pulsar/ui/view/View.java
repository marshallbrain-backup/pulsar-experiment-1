package ui.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import input.Keyboard;
import input.Mouse;
import ui.Ui;
import ui.engine.Point;
import ui.engine.VectorGraphics;
import ui.engine.scripts.ScriptGroup;
import ui.engine.vectors.LinkVector;
import ui.engine.vectors.TextRegion;
import ui.engine.vectors.Vector;
import ui.engine.vectors.VectorGroup;

public class View {
	
	private Map<String, Area> visibleArea;
	
	private VectorGroup activeVectors;
	private ScriptGroup activeScripts;

	public View(VectorGroup av, ScriptGroup as, Object... action) {
		
		activeVectors = av;
		activeScripts = as;
		
		if(activeScripts != null) {
			activeScripts.callFunction("onCreate", action);
		}
		
	}

	public void render(VectorGraphics vg) {
		
		vg.startLogArea();
		
		vg.translationSet(new Point(150, 100));
		activeVectors.draw(vg);
		
		Map<String, Area> a = vg.stopLogArea();
		if(a != null) {
			visibleArea = a;
			
//			Graphics2D g = vg.getGraphics();
//			for(Area i: a.values()) {
//				g.fill(i);
//			}
			
		}
		
	}

	public boolean action(Mouse m, Keyboard k) {
		String a = activeVectors.getAction(m, k, visibleArea);
		if(a != null) {
			System.out.println(a);
		}
		return false;
	}

}