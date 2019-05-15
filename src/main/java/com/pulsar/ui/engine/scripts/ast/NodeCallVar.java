package ui.engine.scripts.ast;


public class NodeCallVar implements NodeCall {
	
	public final String name;
	public final NodeCall parent;
	
	public NodeCallVar(Node n, Node p) {
		name = ((NodeBasic) n).type.ex;
		parent = (NodeCall) p;
	}
	
}
