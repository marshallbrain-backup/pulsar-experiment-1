package ui.engine.scripts.ast;


public class NodeStatementIf implements NodeStatement {

	public final Node condition;
	public final Node code;
	public final Node elseCode;
	
	public NodeStatementIf(Node p, Node c, Node e) {
		condition = p;
		code = c;
		elseCode = e;
	}
	
}
