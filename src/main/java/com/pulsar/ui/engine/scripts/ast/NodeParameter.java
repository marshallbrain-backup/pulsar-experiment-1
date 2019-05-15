package ui.engine.scripts.ast;


public class NodeParameter implements Node {
	
	public final Node[] parameterList;
	
	public NodeParameter(Node[] p){
		parameterList = p;
	}
	
}
