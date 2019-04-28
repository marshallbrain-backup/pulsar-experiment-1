package ui.engine.scripts.token;

public class Token {
	
	public final String ex;
	public final TokenType type;
	
	public Token(String n, TokenType t) {
		ex = n;
		type = t;
	}
	
	public String toString() {
		return ex + "=" + type;
	}

	public boolean equals(Token t) {
		return equals(t.ex, t.type);
	}

	public boolean equals(String e, TokenType k) {
		
		if((k == null || k == type) && (e == null || e.equals(ex))) {
			return true;
		}
		
		return false;
		
	}
	
}
