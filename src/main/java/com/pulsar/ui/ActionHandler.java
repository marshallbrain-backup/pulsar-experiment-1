package ui;

import java.awt.geom.Area;
import java.util.List;
import java.util.Map;

import input.Keyboard;
import input.Mouse;
import ui.engine.actions.Action;
import ui.engine.actions.ActionGroup;
import ui.engine.actions.Effect;
import ui.view.View;

public class ActionHandler {
	
	private List<View> viewList;
	
	private Map<String, ActionGroup> actionList;
	
	public ActionHandler(Map<String, ActionGroup> al, List<View> v) {
		actionList = al;
		viewList = v;
	}

	public boolean performAction(Mouse m, Keyboard k, String path, Area area) {
		
		ActionGroup action = getActionGroup(path);
		
		if(action == null) {
			return false;
		}
		
		for(Action a: action.getActions()) {
			if(a.didAction(m, k, area)) {
				for(Effect e: a.getEffect()) {
					performEffect(e);
				}
				return true;
			}
		}
		
		return false;
		
	}
	
	private ActionGroup getActionGroup(String path) {
		
		while(path.length() != 0) {
			
			ActionGroup a = actionList.get(path);
			if(a != null) {
				return a;
			}
			
			try {
				path = path.substring(0, path.lastIndexOf("."));
			} catch(IndexOutOfBoundsException e) {
				path = "";
			}
			
		}
		
		return null;
		
	}
	
	private void performEffect(Effect e) {
		
		String effect = e.getEffect();
		
		switch(e.getType()) {
			case "open":
				viewList.clear();
				viewList.add(new View(effect));
				break;
			default:
				System.out.println("Unrecognized class " + e.getType());
				System.out.println();
		}
		
	}
	
}
