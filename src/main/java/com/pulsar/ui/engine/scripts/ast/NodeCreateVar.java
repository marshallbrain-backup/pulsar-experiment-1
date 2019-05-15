package ui.engine.scripts.ast;


public class NodeCreateVar implements NodeCreate {
	
	public final String name;
	
	public NodeCreateVar(String n) {
		name = n;
	}
	
}
