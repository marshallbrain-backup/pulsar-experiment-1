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
	
}
