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
import java.util.stream.Collectors;

import ui.engine.scripts.token.Token;
import ui.engine.scripts.token.TokenGroup;
import ui.engine.scripts.token.TokenType;

public class ScriptGroup {

	public ScriptGroup(File file) {
		
		BufferedReader br = null;
		
		try {
			br = Files.newBufferedReader(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String code = br.lines().collect(Collectors.joining(System.lineSeparator()));
		
		Token[] tokens = Lexer.getTokens(code, getExprs());
		
	}
	
	private Token[] getExprs() {
		return new Token[]{
				new Token("\\p{Space}", TokenType.NONE),
				new Token("[#]", TokenType.NONE),
				new Token("[;]", TokenType.NEWLINE),
				new Token("fun", TokenType.ID.FUNCTION)
		};
	}

	public void callFunction(String name, Object... objects) {
	}

	public boolean isEmpty() {
		return false;
	}
	
}
