package ui.engine.scripts.ast;


public class NodeCreateInstance implements NodeCreate {
	
	public final Node name;
	
	public NodeCreateInstance(Node n) {
		name = n;
	}
	
}
