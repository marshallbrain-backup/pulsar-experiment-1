package ui.engine.scripts.ast;

import ui.engine.scripts.token.Token;

public class NodeBasic implements Node {
	
	public final Token type;
	public final Node paramater1;
	public final Node paramater2;
	
	public NodeBasic(Token t) {
		type = t;
		paramater1 = null;
		paramater2 = null;
	}
	
	public NodeBasic(Token t, Node p1) {
		type = t;
		paramater1 = p1;
		paramater2 = null;
	}
	
	public NodeBasic(Token t, Node p1, Node p2) {
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
