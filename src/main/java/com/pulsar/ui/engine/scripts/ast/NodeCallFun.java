package ui.engine.scripts.ast;


public class NodeCallFun implements NodeCall {
	
	public final String name;
	public final Node[] paramaters;
	
	public NodeCallFun(String n, Node[] p) {
		name = n;
		paramaters = p;
	}
	
}
