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
	
	public static <V> HashMap<String, V> getAllMatchingKeys(HashMap<String, V> map, String name, int i) {
		
		HashMap<String, V> newMap = new HashMap<String, V>();
		
		for(Entry<String, V> e: map.entrySet()) {
			String f = e.getKey();
			if(f.matches(name + ".*"))
				newMap.put(f.substring(f.indexOf(f.split("\\.")[i])), e.getValue());
		}
		
		return newMap;
		
	}

	public static <T> HashMap<String, List<T>> getAllMatchingKeys(Map<String, List<T>> map, String name, int i) {
		
		HashMap<String, List<T>> newMap = new HashMap<String, List<T>>();
		
		for(Entry<String, List<T>> e: map.entrySet()) {
			String f = e.getKey();
			if(f.matches(name + ".*"))
				newMap.put(f.substring(f.indexOf(f.split("\\.")[i])), e.getValue());
		}
		
		return newMap;
		
	}

}
