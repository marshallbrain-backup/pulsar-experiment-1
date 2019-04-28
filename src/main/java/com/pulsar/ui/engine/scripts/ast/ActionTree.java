package ui.engine.scripts.ast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import ui.engine.scripts.token.Token;
import ui.engine.scripts.token.TokenType;

public class ActionTree {
	
	public static List<Node> createActionTree(Token[] tokens) {
		
		List<Node> ast = createAST(tokens);
		List<Node> at = createAT(ast);
		
		return null;
		
	}
	
	private static List<Node> createAT(List<Node> ast) {
		
		List<Node> at = new ArrayList<Node>();
		
		for(Node n: ast) {
			at.add(parseNode(n));
		}
		
		return at;
		
	}
	
	private static Node parseNode(Node n) {
		
		if(n instanceof NodeExp) {
			if(((NodeBasic) n.getType()).type.equals("fun", TokenType.KEY)) {
				
				Node title = n.getPar1();
				
				NodeBasic name = (NodeBasic) title.getType();
				NodeList par = (NodeList) title.getPar1().getType();
				NodeList body = (NodeList) n.getPar2().getType();
				
				Node function = new NodeCreateFun(name.type.ex, par, body);
				return function;
				
			}
		}
		
		return null;
		
	}

	private static List<Node> createAST(Token[] tokenArray) {
		
		List<Token> tokenList = new ArrayList<Token>(Arrays.asList(tokenArray));
		tokenList.add(new Token("", TokenType.END));
		Node list = getNodeList(tokenList, 0);
		
		return ((NodeList) list).nodeList;
		
	}
	
	private static Node getNodeList(List<Token> tokens, int start) {
		
		List<Node> body = new ArrayList<Node>();
		Node id = null;
		Token lastToken = null;
		
		for(int i = start; i < tokens.size(); i++) {
			
			Token t = tokens.get(i);
			
			if(t.type == TokenType.NEWLINE) {
				if(tokens.get(i-1).type == TokenType.OP && tokens.get(i-1).ex.equals("{")) {
					body.add(id);
				} else {
					return id;
				}
			} else if(t.type == TokenType.KEY) {
				if(t.ex.equals("fun")) {
					Node funTitle = getNodeList(tokens, i+1);
					Node funBody = getNodeList(tokens, i+2);
					body.add(new NodeExp(new NodeBasic(t), funTitle, funBody));
				}
			} else if(t.type == TokenType.OP) {
				if(t.ex.equals(",")) {
					body.add(id);
				} else if(t.ex.equals(".")) {
					id = new NodeExp(id, getNodeList(tokens, i+1));
				} else if(t.ex.equals("(")) {
					id = new NodeExp(id, getNodeList(tokens, i+1));
				} else if(t.ex.equals(")")) {
						
					if(tokens.get(i-1).type == TokenType.OP && tokens.get(i-1).ex.equals("(")) {
						body.add(id);
						id = new NodeExp(new NodeList(body), new NodeBasic(tokens.get(i-1)), new NodeBasic(tokens.get(i)));
					}
					if(lastToken.type == TokenType.OP && lastToken.ex.equals("(")) {
						tokens.remove(i);
					}
						
					return id;
					
				} else if(t.ex.equals("{")) {
					id = new NodeExp(id, getNodeList(tokens, i+1));
				} else if(t.ex.equals("}")) {
					
					if(!body.isEmpty()) {
						
						if(tokens.get(i-1).type == TokenType.OP && tokens.get(i).type == TokenType.OP) {
							if(tokens.get(i-1).ex.equals("{") && tokens.get(i).ex.equals("}")) {
								id = new NodeExp(new NodeList(body), new NodeBasic(tokens.get(i-1)), new NodeBasic(tokens.get(i)));
								tokens.remove(i);
							}
						}
						
					}
						
					return id;
					
				}
			} else if(t.type == TokenType.ID) {
				id = new NodeBasic(t);
			} else if(t.type == TokenType.STRING) {
				id = new NodeBasic(t);
			} else if(t.type == TokenType.NUM) {
				id = new NodeBasic(t);
			}
			
			lastToken = t;
			tokens.remove(i);
			i--;
			
		}
		
		return new NodeList(body);
		
	}

	private static Node creatFunction(List<Token> tokens) {

		Node name = null;
		Node body = null;
		
		for(int i = 0; i < tokens.size(); i++) {
			Token t = tokens.get(i);
			
			if(t.type == TokenType.ID) {
				name = getNodeList(tokens, i);
			} else if(t.type == TokenType.OP) {
				if(t.ex.equals("{")) {
					body = getNodeList(tokens, i+1);
					break;
				}
			}
			
			tokens.remove(i);
			i--;
		}
		
		return new NodeExp(new NodeBasic(new Token("fun", TokenType.KEY)), name, body);
		
	}
	
}
