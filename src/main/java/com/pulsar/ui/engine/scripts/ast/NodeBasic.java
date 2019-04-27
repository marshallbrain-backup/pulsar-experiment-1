package ui.engine.scripts.ast;

import ui.engine.scripts.token.Token;

public class NodeBasic implements Node {
	
	public final Token type;
	
	public NodeBasic(Token t) {
		type = t;
	}
	
	public String toString() {
		String s = "(" + type;
		return s + ")";
	}
	
}
