package ui.engine.scripts.ast;


public class NodeStatementIf implements NodeStatement {

	public final Node condition;
	public final Node code;
	
	public NodeStatementIf(Node p, Node c) {
		condition = p;
		code = c;
	}
	
}
