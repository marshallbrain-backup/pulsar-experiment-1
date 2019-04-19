package ui.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ui.engine.Point;
import ui.engine.VectorGraphics;
import ui.engine.actions.Action;
import ui.engine.actions.ActionGroup;
import ui.engine.vectors.LinkVector;
import ui.engine.vectors.TextRegion;
import ui.engine.vectors.Vector;
import ui.engine.vectors.VectorGroup;

public class View {
	
	private static Map<String, VectorGroup> vectorList;
	private static Map<String, ActionGroup> actionList;
	
	private VectorGroup activeVectors;
	private ActionGroup activeActions;

	public View(Object[] action) {
		
		initVectors(action);
		
	}
	
	private void initVectors(Object[] action) {
		
		String key = (String) action[0];
		
		System.out.println("Loading vectors from: " + key);

		try {
			activeVectors = vectorList.get(key).clone();
			activeActions = actionList.get(key);
		} catch (NullPointerException e) {
		}
		
		activeVectors.assingParameters(Arrays.copyOfRange(action, 1, action.length));
		
	}

	public static void initGroups(Map<String, VectorGroup> vl, Map<String, ActionGroup> al) {
		vectorList = vl;
		actionList = al;
	}

	public void render(VectorGraphics vg) {
		vg.translationSet(new Point(150, 100));
		activeVectors.draw(vg);
	}

}