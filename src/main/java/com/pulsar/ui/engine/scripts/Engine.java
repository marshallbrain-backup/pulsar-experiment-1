package ui.engine.scripts;

import java.util.List;
import java.util.Map;

import bodys.Body;
import ui.engine.vectors.VectorGroup;
import ui.view.View;

public class Engine {
	
	private List<View> views;
	
	private Map<String, VectorGroup> vectorList;
	private Map<String, ScriptGroup> scriptList;
	
	public Engine(List<View> v, Map<String, VectorGroup> vl, Map<String, ScriptGroup> sl) {
		
		views = v;
		vectorList = vl;
		scriptList = sl;
		
	}

	public void openView(String s, Object... o) {
		System.out.println("view opened - " + s);
		
		VectorGroup vectors = vectorList.get(s);
		ScriptGroup scripts = scriptList.get(s);
		
		views.clear();
		views.add(new View(vectors, scripts, o));
		
	}
	
}
