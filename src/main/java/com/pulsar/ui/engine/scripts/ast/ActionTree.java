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
		
		return at;
		
	}
	
	private static List<Node> createAT(List<Node> ast) {
		
		List<Node> at = new ArrayList<Node>();
		
		for(Node n: ast) {
			at.add(parseNode(n));
		}
		
		return at;
		
	}
	
	private static Node parseNode(Node n) {
		
		Node type = n.getType();
		Node r = null;
		
		if(type.equals(new Token(null, TokenType.KEY), null, null)) {
			if(type.equals(new Token("fun", TokenType.KEY), null, null)) {
				r = parseFunction(n);
			} else if(type.equals(new Token("var", TokenType.KEY), null, null)) {
				r = parseCreateVariable(n);
			} else if(type.equals(new Token("if", TokenType.KEY), null, null)) {
				r = parseIfStatement(n);
			} else if(type.equals(new Token("else", TokenType.KEY), null, null)) {
				r = parseIfStatement(n);
			} else if(type.equals(new Token("new", TokenType.KEY), null, null)) {
				r = parseNewInstance(n);
			}
		} else if(type.equals(new Token(null, TokenType.ID), null, null)) {
			r = parseVariable(n);
		} else if(type.equals(new Token(null, TokenType.LITERAL), null, null)) {
			
			Token t = (Token) ((NodeBasic) type).type;
			
			if(t.ex.matches("^\"[\\p{ASCII}&&[^\"]]*\"")) {
				r = new NodeLitteralString(t.ex.substring(1, t.ex.length()-1));
			} else if(t.ex.matches("^[0-9]*")) {
				r = new NodeLitteralInt(t.ex.substring(0, t.ex.length()));
			}
		}
		
		return r;
		
	}
	
	private static Node parseNewInstance(Node n) {
		
		Node instance = parseNode(n.getPar1());
		Node var = new NodeCreateInstance(instance);
		
		return var;
		
	}

	private static Node parseIfStatement(Node n) {
		
		Node statement = null;
		
		if(n.getType().equals(new Token("if", TokenType.KEY), null, null)) {
			
			Node condition = parseList(n.getType().getPar1().getPar1().getType()).nodeList.get(0);
			Node code = parseList(n.getPar1().getType());
			Node elseCode = parseIfStatement(n.getPar2());
			statement = new NodeStatementIf(condition, code, elseCode);
			
		} else if(n.equals(new Token("else", TokenType.KEY), null, null)) {

			Node code = parseList(n.getPar1().getType());
			statement = new NodeStatementElse(code);
			
		}
		
		return statement;
		
	}

	private static Node parseCreateVariable(Node n) {
		
		NodeBasic name = (NodeBasic) n.getPar1().getType();
		Node value = parseNode(n.getPar1().getPar1());
		Node var = new NodeCreateVar(name.type.ex);
		Node varCall = new NodeAssignVar(var, value);
		
		return varCall;
		
	}

	private static Node parseVariable(Node n) {
		
		List<Node> varChain = new ArrayList<Node>();
		Node var = null;
		Node p = n;
		
		while(p.getPar1() != null && p.getPar1().getType().equals(new Token(null, TokenType.ID), null, null)) {
			varChain.add(p.getType());
			p = p.getPar1();
		}
		
		for(Node v: varChain) {
			var = new NodeCallVar(v, var);
		}
		
		if(p.getPar1() != null) {
			NodeList par = (NodeList) parseFunction(p);
			Node[] l = new Node[par.nodeList.size()];
			var = new NodeCallFun(p.getType(), par.nodeList.toArray(l), var);
		} else {
			var = new NodeCallVar(p, var);
		}
		
		
		return var;
	}
	
	private static Node parseFunction(Node n) {
		
		Node r = null;
		
		if(n.getType().equals(new Token("fun", TokenType.KEY), null, null)) {
			NodeBasic name = (NodeBasic) n.getPar1().getType();
			NodeList par = parseList(n.getPar1().getPar1().getType());
			NodeList body = parseList(n.getPar2().getType());
			r = new NodeCreateFun(name.type.ex, par, body);
		} else {
			r = parseList(n.getPar1().getType());
		}
		
		return r;
	}
	
	private static NodeList parseList(Node list) {
		
		List<Node> nodeList = new ArrayList<Node>();
		NodeList node = (NodeList) list;
		
		for(Node n: node.nodeList) {
			if(n != null) {
				nodeList.add(parseNode(n));
			}
		}
		
		return new NodeList(nodeList);
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
					Node funBody = getNodeList(tokens, i+1);
					body.add(new NodeExp(new NodeBasic(t), funTitle, funBody));
				} else if(t.ex.equals("var")) {
					Node var = getNodeList(tokens, i+1);
					id = new NodeExp(new NodeBasic(t), var);
				} else if(t.ex.equals("new")) {
					Node object = getNodeList(tokens, i+1);
					id = new NodeExp(new NodeBasic(t), object);
				} else if(t.ex.equals("if")) {
					Node condition = getNodeList(tokens, i+1);
					Node code = getNodeList(tokens, i+1);
					Node ifState = new NodeExp(new NodeBasic(t), condition);
					Node elseState = getNodeList(tokens, i+1);
					id = new NodeExp(ifState, code, elseState);
					body.add(id);
				} else if(t.ex.equals("else")) {
					Node code = getNodeList(tokens, i+1);
					id = new NodeExp(new NodeBasic(t), code);
					tokens.remove(i);
					return id;
				} else {
					System.out.println(t);
				}
				
			} else if(t.type == TokenType.OP) {
				
				if(t.ex.equals(",")) {
					body.add(id);
				} else if(t.ex.equals("=")) {
					Node assign = getNodeList(tokens, i+1);
					id = new NodeExp(id, assign);
				} else if(t.ex.equals(".")) {
					id = new NodeExp(id, getNodeList(tokens, i+1));
				} else if(t.ex.equals("(")) {
					id = new NodeExp(id, getNodeList(tokens, i+1));
				} else if(t.ex.equals(")")) {
						
					if(tokens.get(i-1).type == TokenType.OP && tokens.get(i-1).ex.equals("(")) {
						body.add(id);
						id = new NodeExp(new NodeList(body), new NodeBasic(tokens.get(i-1)), new NodeBasic(tokens.get(i)));
						tokens.remove(i);
						return id;
					}
					
//					if(lastToken != null && !(lastToken.type == TokenType.OP && lastToken.ex.equals("("))) {
						return id;
//					}
					
				} else if(t.ex.equals("{")) {
					
					if(lastToken != null && lastToken.type != TokenType.KEY) {
						return id;
					}
					
					id = getNodeList(tokens, i+1);
					id = new NodeExp(id, new NodeBasic(tokens.get(i)), new NodeBasic(tokens.get(i+1)));
					tokens.remove(i);
					tokens.remove(i);
					return id;
					
				} else if(t.ex.equals("}")) {
					return new NodeList(body);
				} else {
					System.out.println(t);
				}
				
			} else if(t.type == TokenType.ID) {
				id = new NodeBasic(t);
			} else if(t.type == TokenType.LITERAL) {
				id = new NodeBasic(t);
			} else if(t.type == TokenType.END){
				return new NodeList(body);
			} else {
				System.out.println(t);
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
