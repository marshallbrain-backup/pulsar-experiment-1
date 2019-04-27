package ui.engine.scripts.ast;


public class NodeCallVar implements NodeCall {
	
	public final String name;
	public final Node parent;
	
	public NodeCallVar(String n, Node p) {
		name = n;
		parent = p;
	}
	
}
