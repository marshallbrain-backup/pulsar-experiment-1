package ui.engine.scripts;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ui.engine.scripts.token.Token;
import ui.engine.scripts.token.TokenType;

public class Lexer {
	
	public static Token[] getTokens(String code, Token[] tokenExprs) {

		int pos = 0;
		List<Token> tokens = new ArrayList<Token>();
		
		while(pos < code.length()) {
			
			boolean matches = false;
			Matcher m = null;
			
			for(Token e: tokenExprs) {
				
				Pattern r = Pattern.compile(e.ex);
				m = r.matcher(code.substring(pos));
				
				if(m.find()) {
					matches = true;
					String t = m.group(0);
					if(e.type != TokenType.NONE) {
						tokens.add(new Token(t, e.type));
					}
					break;
				}
				
			}
			
			if(!matches) {
				pos++;
			} else {
				String e = code.substring(0, pos+m.start(0));
				if(!e.isEmpty()) {
					System.out.println("Unknown charactor: " + e);
				}
				code = code.substring(pos+m.end(0));
				pos = 0;
			}
			
		}
		
		if(!code.isEmpty())
			System.out.println("Unknown charactor: " + code);
		
		Token[] t = new Token[tokens.size()];
		t = tokens.toArray(t);
		return t;
		
	}
	
}
