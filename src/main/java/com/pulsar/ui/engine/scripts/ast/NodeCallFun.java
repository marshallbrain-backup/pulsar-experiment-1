package ui.engine.scripts.ast;


public class NodeCallFun implements NodeCall {
	
	public final String name;
	public final Node[] paramaters;
	public final NodeCall parent;
	
	public NodeCallFun(Node n, Node[] p, Node pa) {
		name = ((NodeBasic) n).type.ex;
		paramaters = p;
		parent = (NodeCall) pa;
	}
	
}
