package ui.engine.scripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import bodys.Body;
import ui.engine.scripts.token.Token;
import ui.engine.scripts.token.TokenGroup;
import ui.engine.scripts.token.TokenType;

public class ScriptGroup {
	
	private Map<String, Object[]> functions = new HashMap<String, Object[]>();

	public ScriptGroup(File file) {
		
		BufferedReader br = null;
		
		try {
			br = Files.newBufferedReader(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String code = br.lines().collect(Collectors.joining(System.lineSeparator()));
		
		Token[] tokens = Lexer.getTokens(code, getExprs());
		
		for(int i = 0; i < tokens.length; i++) {
			if(tokens[i].type == TokenType.KEY.FUN) {
				
				String name = tokens[i+1].ex;;
				List<String> par = new ArrayList<String>();
				List<Token> body = new ArrayList<Token>();
				
				boolean gotVar = false;
				if(tokens[i+2].type == TokenType.OP.OPAR) {
					for(int j = i+3; tokens[j].type != TokenType.OP.CPAR; j++, i=j+1) {
						
						if(!gotVar && tokens[j].type == TokenType.VAR) {
							par.add(tokens[j].ex);
							gotVar = true;
						} else if(gotVar && tokens[j].type == TokenType.OP.COMA) {
							gotVar = false;
						} else {
							System.out.println("error");
						}
						
					}
				}
				
				int braceCount = 0;
				if(tokens[i].type == TokenType.OP.OBRACE) {
					for(int j = i+1; !(braceCount == 0 && tokens[j].type == TokenType.OP.CBRACE); j++, i=j) {
						
						if(tokens[j].type == TokenType.OP.OBRACE) {
							braceCount++;
						} else if(tokens[j].type == TokenType.OP.CBRACE) {
							braceCount--;
						}
						
						body.add(tokens[j]);
					}
				}
				
				Object[] fun = new Object[2];
				String[] s = new String[par.size()];
				Token[] t = new Token[body.size()];
				
				fun[0] = par.toArray(s);
				fun[1] = body.toArray(t);
				
				functions.put(name, fun);
				
			}
		}
		
	}

	public void callFunction(String functionName, Object body) {
		
	}

	public boolean isEmpty() {
		return false;
	}
	
	private Token[] getExprs() {
		return new Token[]{
				new Token("^\\p{Space}", TokenType.NONE),
				new Token("^[#]", TokenType.NONE),
				new Token("^[;]", TokenType.NEWLINE),
				new Token("^fun", TokenType.KEY.FUN),
				new Token("^\\(", TokenType.OP.OPAR),
				new Token("^\\)", TokenType.OP.CPAR),
				new Token("^\\{", TokenType.OP.OBRACE),
				new Token("^\\}", TokenType.OP.CBRACE),
				new Token("^,", TokenType.OP.COMA),
				new Token("^\"\\p{ASCII}*\"", TokenType.LIT.STRING),
				new Token("^[a-zA-Z][a-zA-Z_0-9]*(?=\\.)", TokenType.ID),
				new Token("^[a-zA-Z][a-zA-Z_0-9]*(?=\\()", TokenType.FUN),
				new Token("^[a-zA-Z][a-zA-Z_0-9]*", TokenType.VAR),
				new Token("^[0-9]", TokenType.LIT.INT),
				new Token("^\\.", TokenType.OP.DOT),
		};
	}
	
}
