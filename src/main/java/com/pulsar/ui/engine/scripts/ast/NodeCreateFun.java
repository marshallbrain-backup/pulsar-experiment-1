package ui.engine.scripts.ast;


public class NodeCreateFun implements NodeCreate {
	
	public final String name;
	public final Node[] paramaters;
	public final Node[] body;
	
	public NodeCreateFun(String n, Node[] p, Node[] b) {
		name = n;
		paramaters = p;
		body = b;
	}
	
}
