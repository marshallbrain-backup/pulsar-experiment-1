package ui.engine.scripts.ast;

public class NodeExp implements Node {
	
	public final Node type;
	public final Node paramater1;
	public final Node paramater2;
	
	public NodeExp(Node t) {
		type = t;
		paramater1 = null;
		paramater2 = null;
	}
	
	public NodeExp(Node t, Node p1) {
		type = t;
		paramater1 = p1;
		paramater2 = null;
	}
	
	public NodeExp(Node t, Node p1, Node p2) {
		type = t;
		paramater1 = p1;
		paramater2 = p2;
	}
	
	public String toString() {
		String s = "(" + type;
		if(paramater1 != null) {
			s += "; " + paramater1;
		}
		if(paramater2 != null) {
			s += ", " + paramater2;
		}
		return s + ")";
	}
	
}
