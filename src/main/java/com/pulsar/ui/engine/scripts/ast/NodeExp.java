package ui.engine.scripts.ast;

import java.util.Arrays;

import ui.engine.scripts.token.Token;
import ui.engine.scripts.token.TokenType;

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
	
	@Override
	public Node getType() {
		return type;
	}

	@Override
	public Node getPar1() {
		return paramater1;
	}

	@Override
	public Node getPar2() {
		return paramater2;
	}

	@Override
	public boolean equals(Token a, Token b, Token c) {
		
		if(a != null) {
			return type.equals(a, null, null);
		}
		if(b != null) {
			return paramater1.equals(b, null, null);
		}
		if(c != null) {
			return paramater2.equals(c, null, null);
		}
		
		return false;
	}
	
}
