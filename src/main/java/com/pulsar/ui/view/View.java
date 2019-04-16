package ui.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ui.engine.Point;
import ui.engine.VectorGraphics;
import ui.engine.actions.Action;
import ui.engine.actions.ActionGroup;
import ui.engine.vectors.LinkVector;
import ui.engine.vectors.Vector;
import ui.engine.vectors.VectorGroup;

public class View {
	
	private static Map<String, VectorGroup> vectorList;
	private static Map<String, ActionGroup> actionList;
	
	private List<Vector> activeVectors;
	private List<Action> activeActions;

	public View(Object[] action) {
		
		activeVectors = new ArrayList<Vector>();
		activeActions = new ArrayList<Action>();
		
		initVectors(action);
		
	}
	
	private void initVectors(Object[] action) {
		
		VectorGroup av = null;
		ActionGroup aa = null;
		
		String key = (String) action[0];
		
		System.out.println("Loading vectors from: " + key);

		try {
			av = vectorList.get(key);
			aa = actionList.get(key);
			activeVectors.addAll(av.getVectors());
			activeActions.addAll(aa.getActions());
		} catch (NullPointerException e) {
		}
		
		av.assingParameters(action);
		
		for(int i = 0; i < activeVectors.size(); i++) {
			if(activeVectors.get(i).getType().equals("link vector")) {
				LinkVector link = (LinkVector) activeVectors.get(i);
				activeVectors.remove(i);
				i--;
				initVectors(link.getParamerters());
			}
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