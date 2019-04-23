package ui.engine.scripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ScriptGroup {
	
	private Map<String, Function> functionMap;
	private Map<String, Object> varMap;

	public ScriptGroup(File file) {
		
		functionMap = new HashMap<String, Function>();
		varMap = new HashMap<String, Object>();
		
		FileInputStream is = null;
		char[] charList = null;
		try {
			is = new FileInputStream(file);
			charList = new char[is.available()];
			InputStreamReader isr = new InputStreamReader(is);
			isr.read(charList);
			isr.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(charList == null) {
			return;
		}
			
		List<String> tokenList = new ArrayList<String>();
		
		String line = "";
		String type = "";
		
		for(char c: charList) {
			if(isEscape(c)) {
				add(tokenList, type, line);
				line = String.valueOf(c);
				type = "ESCAPE";
			} else if(isLetter(c) || isNumber(c)) {
				if(type.equals("NAME")) {
					line += String.valueOf(c);
				} else {
					add(tokenList, type, line);
					line = String.valueOf(c);
					type = "NAME";
				}
			} else if(isOpp(c)) {
				add(tokenList, type, line);
				line = String.valueOf(c);
				type = "OP";
			} else if(c == ' ') {
				add(tokenList, type, line);
				line = "";
				type = "";
			}
		}
		
		add(tokenList, type, line);
		
		List<List<String>> tokenLines = new ArrayList<List<String>>();
		List<String> tokens = new ArrayList<String>();
		String currentEscapeList = "";
		
		for(String s: tokenList) {
			if(s.startsWith("ESCAPE")) {
				
				String a = s.split(":")[1];
				
				switch(a) {
					case "\t":
						break;
					case "\r":
					case "\n":
						currentEscapeList += a;
						break;
				}
				
				if(currentEscapeList.equals(System.lineSeparator())) {
					currentEscapeList = "";
					tokenLines.add(tokens);
					tokens = new ArrayList<String>();
				}
				
			} else {
				tokens.add("" + s + "");
			}
		}
		
		tokenLines.add(tokens);
		
		for(List<String> l: tokenLines) {
			Iterator<String> i = l.iterator();
			String t = "";
			while(i.hasNext()) {
				t = i.next();
				if(checkToken(t, "NAME", "fun")) {
					createFunction(i);
				}
			}
		}
		
	}
	
	private void createFunction(Iterator<String> i) {
		
		String t = "";
		String name = i.next();
		List<String> pars = new ArrayList<String>();
		
		if(checkToken((t = i.next()), "OP", "(")) {
			
			String currentPar = "";
			
			while(!checkToken((t = i.next()), "OP", ")")) {
				if(t.equals("OP:,")) {
					pars.add(currentPar);
					currentPar = "";
				} else {
					currentPar += t;
				}
			}
			
			pars.add(currentPar);
			currentPar = "";
			
		}
		
		Function fun = new Function(pars);
		functionMap.put(name, fun);
		
	}
	
	public void callFunction(String name, Object... objects) {
		functionMap.get(name).call(objects);
	}
	
	private boolean checkToken(String s, String t, String e) {
		if(s.equals("" + t + ":" + e + "")) {
			return true;
		}
		return false;
	}

	private void add(List<String> tokens, String type, String line) {
		if(!type.isEmpty()) {
			tokens.add(type + ":" + line);
		}
	}

	private boolean isLetter(char c) {
		if(Character.toString(c).matches("[a-zA-Z]")) {
			return true;
		}
		return false;
	}
	
	private boolean isNumber(char c) {
		if(Character.toString(c).matches("[0-9]")) {
			return true;
		}
		return false;
	}
	
	private boolean isEscape(char c) {
		switch(c) {
			case '\t':
			case '\r':
			case '\n':
				return true;
		}
		return false;
	}
	
	private boolean isOpp(char c) {
		if(Character.toString(c).matches("[^a-z0-9 ]")) {
			return true;
		}
		return false;
	}

	public boolean isEmpty() {
		return true;
	}
	
}
