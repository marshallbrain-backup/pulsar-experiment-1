package math;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Other {

	public static String getExtension(File file) {
		String name = file.getName();
		int i = name.lastIndexOf('.');
		return name.substring(i+1);
	}
	
	public static <T> Map<String, T> getAllMatchingKeys(Map<String, T> map, String name, int i) {
		
		Map<String, T> newMap = new HashMap<String, T>();
		
		for(Entry<String, T> e: map.entrySet()) {
			String f = e.getKey();
			if(f.matches(name + ".*"))
				newMap.put(f.substring(f.indexOf(f.split("\\.")[i])), e.getValue());
		}
		
		return newMap;
		
	}
	
	public static Color getColor(String hex, String alpha) {
		
		Color c = null;
			
		if(hex.startsWith("#")) {
			
			hex = hex.substring(1);
			
			int r = Integer.parseInt(hex.substring(0, 2), 16);
			int g = Integer.parseInt(hex.substring(2, 4), 16);
			int b = Integer.parseInt(hex.substring(4, 6), 16);
			int a = Math.round(Float.parseFloat(alpha)*255);
			
			c = new Color(r, g, b, a);
			
		}
		
		return c;
		
	}

}
