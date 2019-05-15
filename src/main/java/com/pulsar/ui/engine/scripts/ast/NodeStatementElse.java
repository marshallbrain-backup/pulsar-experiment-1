package ui.engine.scripts.ast;


public class NodeStatementElse implements NodeStatement {
	
	public final Node code;
	
	public NodeStatementElse(Node c) {
		code = c;
	}
	
}
