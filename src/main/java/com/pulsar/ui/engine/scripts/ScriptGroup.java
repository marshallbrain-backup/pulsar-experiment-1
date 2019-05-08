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
import ui.engine.scripts.ast.ActionTree;
import ui.engine.scripts.ast.Node;
import ui.engine.scripts.ast.NodeCreateFun;
import ui.engine.scripts.token.Token;
import ui.engine.scripts.token.TokenType;
import ui.engine.vectors.VectorGroup;

public class ScriptGroup {
	
	private Map<String, NodeCreateFun> functionList;

	public ScriptGroup(File file) {
		
		BufferedReader br = null;
		functionList = new HashMap<String, NodeCreateFun>();
		
		try {
			br = Files.newBufferedReader(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String code = br.lines().collect(Collectors.joining(System.lineSeparator()));
		
		Token[] tokens = Lexer.getTokens(code, getExprs());
		
		List<Node> at = ActionTree.createActionTree(tokens);
		for(Node n: at) {
			if(n instanceof NodeCreateFun) {
				NodeCreateFun f = (NodeCreateFun) n;
				functionList.put(f.name, f);
			}
		}
		
	}

	public void callFunction(String functionName, VectorGroup vg, Object... objects) {
		
		NodeCreateFun fun = functionList.get(functionName);
		if(fun != null) {
			Interpreter.runFunction(fun, vg, objects, functionList);
		}
	}

	public boolean isEmpty() {
		return false;
	}
	
	private Token[] getExprs() {
		return new Token[]{
				new Token("^\\p{Space}", TokenType.NONE),
				new Token("^[#]", TokenType.NONE),
				
				new Token("^[;]", TokenType.NEWLINE),
				
				new Token("^fun", TokenType.KEY),
				new Token("^var", TokenType.KEY),
				new Token("^if", TokenType.KEY),
				new Token("^else", TokenType.KEY),
				new Token("^new", TokenType.KEY),
				
				new Token("^\\(", TokenType.OP),
				new Token("^\\)", TokenType.OP),
				new Token("^\\{", TokenType.OP),
				new Token("^\\}", TokenType.OP),
				new Token("^,", TokenType.OP),
				new Token("^=", TokenType.OP),
				new Token("^\\.", TokenType.OP),
				
				new Token("^[a-zA-Z][a-zA-Z_0-9]*", TokenType.ID),
				
				new Token("^\"[\\p{ASCII}&&[^\"]]*\"", TokenType.LITERAL),
				new Token("^[0-9][a-zA-Z_0-9]*", TokenType.LITERAL),
				
		};
	}
	
}
