package ui.engine.scripts.token;


public enum TokenType implements TokenGroup {
	
	NONE,
	NEWLINE,
	ID,
	FUN,
	VAR,
	OTHER;
	
	public enum KEY implements TokenGroup {
		
		FUN
		
	}
	
	public enum OP implements TokenGroup {
		
		OPAR,
		CPAR,
		OBRACE,
		CBRACE,
		COMA,
		DOT
		
	}
	
	public enum LIT implements TokenGroup {
		
		STRING,
		INT
		
	}
	
}
