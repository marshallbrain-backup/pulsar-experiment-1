package math;

import java.io.File;
import java.util.HashMap;
import java.util.List;
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

}
