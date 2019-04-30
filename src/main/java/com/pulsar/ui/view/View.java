package ui.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ui.Ui;
import ui.engine.Point;
import ui.engine.VectorGraphics;
import ui.engine.scripts.ScriptGroup;
import ui.engine.vectors.LinkVector;
import ui.engine.vectors.TextRegion;
import ui.engine.vectors.Vector;
import ui.engine.vectors.VectorGroup;

public class View {
	
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
		vg.translationSet(new Point(150, 100));
		activeVectors.draw(vg);
	}

}