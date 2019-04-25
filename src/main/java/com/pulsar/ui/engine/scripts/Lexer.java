package ui.engine.scripts;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
	
	public static String[][] getTokens(String code, String[][] tokenExprs) {
		
		int pos = 0;
		List<String[]> tokens = new ArrayList<String[]>();
		
		while(pos < code.length()) {
			
			boolean matches = false;
			Matcher m = null;
			
			for(String[] e: tokenExprs) {
				
				Pattern r = Pattern.compile(e[0]);
				m = r.matcher(code.substring(0, pos+1));
				
				if(m.find()) {
					matches = true;
					String t = m.group(0);
					if(!e[1].equals("NONE")) {
						tokens.add(new String[] {t, e[1]});
					}
					break;
				}
				
			}
			
			if(!matches) {
				pos++;
			} else {
				String e = code.substring(0, m.start(0));
				System.out.println("Unknown charactor: " + e);
				code = code.substring(m.end(0));
				pos = 0;
			}
			
		}
		
		String[][] t = new String[tokens.size()][2];
		t = tokens.toArray(t);
		return t;
		
	}
	
}
