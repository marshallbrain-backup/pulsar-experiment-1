package ui;

import java.util.Map;

import ui.engine.actions.ActionGroup;
import ui.engine.vectors.VectorGroup;

public class ActionHandler {
	
	Map<String, VectorGroup> vectorList;
	Map<String, ActionGroup> actionList;
	
	public ActionHandler(Map<String, VectorGroup> vl, Map<String, ActionGroup> al) {
		vectorList = vl;
		actionList = al;
	}
	
}
