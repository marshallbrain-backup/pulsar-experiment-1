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
		
		return null;
		
	}
	
	private static List<Node> createAST(Token[] tokenArray) {
		
		Iterator<Token> tokens = Arrays.asList(tokenArray).iterator();
		NodeList list = getNodeList(tokens);
		System.out.println(list);
		
		return list.nodeList;
		
	}
	
	private static NodeList getNodeList(Iterator<Token> tokens) {
		
		List<Node> body = new ArrayList<Node>();
		Token name = null;
		
		while(tokens.hasNext()) {
			Token t = tokens.next();
			if(t.type == TokenType.KEY) {
				if(t.ex.equals("fun")) {
					body.add(createFunction(tokens));
				}
			}
		}
		
		return new NodeList(body);
		
	}
	
	private static Node readLine(Iterator<Token> tokens) {
		
		List<Node> body = new ArrayList<Node>();
		Token current = null;
//		body.add(new NodeBasic(current, ));
		
		while(tokens.hasNext()) {
			Token t = tokens.next();
			if(t.ex.equals("this")) {
				System.out.print("");
			}
			if(t.type == TokenType.OP) {
				if(t.ex.equals("{")) {
					return readLine(tokens);
				} else if(t.ex.equals("}")) {
					return new NodeList(body);
				} else if(t.ex.equals(".")) {
					body.add(new NodeBasic(current, readLine(tokens)));
					current = null;
				} else if(t.ex.equals("(")) {
					return new NodeBasic(current, readLine(tokens));
				} else if(t.ex.equals(",")) {
					body.add(new NodeBasic(current));
					current = null;
				} else if(t.ex.equals(")")) {
					return new NodeList(body);
				}
			} else if(t.type == TokenType.ID || t.type == TokenType.STRING || t.type == TokenType.NUM) {
				if(current != null) {
					System.out.println("Function initializing error: " + t.ex);
				} else {
					current = t;
				}
			}
		}
		
		return new NodeList(body);
		
	}

	private static Node createFunction(Iterator<Token> tokens) {
		
		Node par = null;
		
		Token name = null;
		
		while(tokens.hasNext()) {
			Token t = tokens.next();
			if(name == null && t.type == TokenType.ID) {
				name = t;
			} else if(t.type == TokenType.OP && t.ex.equals("(")) {
				par = getParamaters(tokens);
				break;
			}
			
		}
		
		Node fun = new NodeBasic(name, par);
		
		Node body = readLine(tokens);
		Node node = new NodeBasic(new Token("fun", TokenType.KEY), fun, body);
		
		return node;
	}
	
	private static Node getParamaters(Iterator<Token> tokens) {

		List<Node> paramaters = new ArrayList<Node>();
		Node currentPar = null;
		
		while(tokens.hasNext()) {
			Token t = tokens.next();
			if(t.type == TokenType.OP && t.ex.equals(")")) {
				return new NodeList(paramaters);
			} else if(t.type == TokenType.OP && t.ex.equals(",")) {
				currentPar = null;
			} else if(t.type == TokenType.ID) {
				if(currentPar != null) {
					System.out.println("Function initializing error: " + t.ex);
				} else {
					currentPar = new NodeBasic(t);
					paramaters.add(currentPar);
				}
			} else {
				System.out.println("Function initializing error: " + t.ex);
			}
		}
		
		return new NodeList(paramaters);
		
	}
	
}
