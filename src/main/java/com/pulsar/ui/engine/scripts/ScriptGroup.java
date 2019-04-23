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
import java.util.List;
import java.util.Map;

public class ScriptGroup {

	public ScriptGroup(File file) {
		
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
			
		List<String> tokens = new ArrayList<String>();
		
		String line = "";
		String type = "";
		
		for(char c: charList) {
			if(!String.valueOf(c).matches(".")) {
				add(tokens, type, line);
				line = String.valueOf(c);
				type = "ESCAPE";
			} else if(isLetter(c) || isNumber(c)) {
				if(type.equals("NAME")) {
					line += String.valueOf(c);
				} else {
					add(tokens, type, line);
					line = String.valueOf(c);
					type = "NAME";
				}
			} else if(isOpp(c)) {
				add(tokens, type, line);
				line = String.valueOf(c);
				type = "OPP";
			} else if(c == ' ') {
				add(tokens, type, line);
				line = "";
				type = "";
			}
		}
		
		add(tokens, type, line);
		
		System.out.println(tokens.toString());
		
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
