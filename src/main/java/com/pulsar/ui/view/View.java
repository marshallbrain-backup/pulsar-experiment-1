package ui.view;

import java.util.List;
import java.util.Map;

import ui.engine.Point;
import ui.engine.VectorGraphics;
import ui.engine.actions.Action;
import ui.engine.actions.ActionGroup;
import ui.engine.vectors.Vector;
import ui.engine.vectors.VectorGroup;

public class View {
	
	private static Map<String, VectorGroup> vectorList;
	private static Map<String, ActionGroup> actionList;
	
	private List<Vector> activeVectors;
	private List<Action> activeActions;

	public View(String action) {
		
		int s = action.indexOf(";");
		
		String par = action.substring(s+1);
		String key = action.substring(0, s);
		
		try {
			activeVectors = vectorList.get(key).getVectors();
			activeActions = actionList.get(key).getActions();
		} catch (NullPointerException e) {
		}
		
		if(activeVectors == null) {
			throw new NullPointerException();
		}
		
	}

	public static void initGroups(Map<String, VectorGroup> vl, Map<String, ActionGroup> al) {
		vectorList = vl;
		actionList = al;
	}

	public void render(VectorGraphics vg) {
		
		vg.translationSet(new Point(150, 100));
		
		for(Vector v: activeVectors) {
			vg.draw(v);
		}
		
	}

}