package files;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GameFile {
	
	private String name;
	
	private HashMap<String, GameFile> gameFiles;
	private HashMap<String, String> fields;
	private HashMap<String, String> vars;
	
	/**
	 * Initalizes gamefile
	 * 
	 * @param name name of the gamefile
	 */
	public GameFile(String n) {
		
		name = n;

		gameFiles = new HashMap<String, GameFile>();
		fields = new HashMap<String, String>();
		vars = new HashMap<String, String>();
		
	}
	
//	/**
//	 * Initalizes gamefile based on a hashmap
//	 * 
//	 * @param file hashmap containing fields
//	 */
//	public GameFile(HashMap<String, String> f) {
//		fields = f;
//	}

	public void add(GameFile f) {
		gameFiles.put(f.name, f);
	}
	
	/**
	 * Adds a field
	 * 
	 * @param field name of the field
	 * @param var	name of the variable
	 */
	public void add(String f, String v) {
		add(f, v, 0);
	}
	
	/**
	 * Adds a field
	 * 
	 * @param field		name of the field
	 * @param var		name of the variable
	 * @param position 	position of the number for dublicite fields
	 */
	public void add(String f, String v, int p) {
		
		String a = f.split("\\.")[p]; //get the target field value for numbering
		
		//if field starts with @ than add it as a variable
		if(a.startsWith("@")) {
			vars.put(a, v);
		} else {
			
			int b = f.indexOf(a)+a.length(); //get position of the end of the target field
			
			String subA = f.substring(0, b); //get upto the target field
			String subB = f.substring(b); //get after the target field
			
			//if a dublicit exist than add with the first avalible number
			if(fields.containsKey(subA + "0" + subB)) {
				for(int i = 0; fields.putIfAbsent(subA + String.valueOf(i) + subB, v) != null; i++);
			} else {
				
				//add the field and value if non exist
				String pastV = fields.putIfAbsent(f, v);
				
				//if value is present for the field replace it with numbers
				if(pastV != null) {
					fields.remove(f);
					fields.putIfAbsent(subA + 0 + subB, pastV);
					fields.putIfAbsent(subA + 1 + subB, v);
				}
				
			}
		
		}
		
	}
	
	/**
	 * @return returns the fields
	 */
	public HashMap<String, String> getFields() {
		return fields;
	}
	
	/**
	 * @param name name of the field
	 * @return returns the spesified field or "1" if it does not exsit
	 */
	public String getField(String name) {
		return fields.getOrDefault(name, null);
	}
	
	/**
	 * @param name name of the field
	 * @return returns the first field that fits the regular expretion
	 */
	public String getFieldOne(String name) {
		HashMap<String, String> h = getFieldAll(name);
		return h.get(h.keySet().toArray()[0]);
	}
	
	/**
	 * Returns all the fields that fit the regular expretion
	 * 
	 * @param name name of the field
	 * @return hashmap of the fields
	 */
	public HashMap<String, String> getFieldAll(String name) {
		
		HashMap<String, String> o = new HashMap<String, String>();
		
		for(Entry<String, String> e: fields.entrySet()) {
			String f = e.getKey();
			if(f.matches(name + ".*"))
				o.put(f, e.getValue());
		}
		
		return o;
		
	}
	
	public void comit() {
		for(Entry<String, GameFile> e: gameFiles.entrySet()) {
			fields.putAll(e.getValue().fields);
			vars.putAll(e.getValue().vars);
		}
		gameFiles.clear();
	}
	
	/**
	 * replases all values for fields that start with '@' with the coraspanding variable
	 */
	public void replaceVars() {
		for(Entry<String, String> e: fields.entrySet()) {
			if(e.getValue().contains("@")) {
				fields.replace(e.getKey(), vars.get(e.getValue()));
			}
		}
	}
	
	public String toString() {
		return fields.toString();
	}

	public String getName() {
		return name;
	}

	public static List<Map<String, String>> convertFiles(HashMap<String, String> fieldAll) {
		
		HashMap<String, HashMap<String, String>> gf = new HashMap<String, HashMap<String, String>>();
		
		for(Entry<String, String> e: fieldAll.entrySet()) {
			String[] p = e.getKey().split("\\.");
			HashMap<String, String> g = gf.getOrDefault(p[1], new HashMap<String, String>());
			if(g.isEmpty()) g.put("", p[1]);
			g.put(e.getKey().substring(e.getKey().indexOf(p[2])), e.getValue());
			gf.putIfAbsent(p[1], g);
		}
		
		List<Map<String, String>> a = new ArrayList<Map<String, String>>(gf.values());
		
		return a;
		
	}

}
