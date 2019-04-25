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

public class ScriptGroup {

	public ScriptGroup(File file) {
		
		BufferedReader br = null;
		
		try {
			br = Files.newBufferedReader(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String code = br.lines().collect(Collectors.joining(System.lineSeparator()));
		
		Lexer.getTokens(code, getExprs());
		
	}
	
	private String[][] getExprs() {
		return new String[][]{
			{"[ \\t\\x0B\\f]", "NONE"},
			{"[#]", "NONE"},
			{System.lineSeparator().replace("\n", "\\n").replace("\r", "\\r"), "NEWLINE"},
		};
	}

	public void callFunction(String name, Object... objects) {
	}

	public boolean isEmpty() {
		return false;
	}
	
}
