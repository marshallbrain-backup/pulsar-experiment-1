package ui.engine.scripts.ast;

import ui.engine.scripts.token.Token;
import ui.engine.scripts.token.TokenType;

public interface Node {
	
	default Node getType() {
		return null;
	}

	default Node getPar1() {
		return null;
	}

	default Node getPar2() {
		return null;
	}

	default boolean equals(Token a, Token b, Token c) {
		return false;
	}
	
}
