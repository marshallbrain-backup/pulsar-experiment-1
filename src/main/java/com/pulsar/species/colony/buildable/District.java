package species.colony.buildable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import files.GameFile;
import files.VarInterface;
import files.VarName;
import files.VariableArray;
import math.Other;
import species.colony.Colony;
import species.colony.ResourceManager;

public class District {
	
	private int baseBuildTime;
	
	private String type;
	
	private Map<String, String> cost;
	private Map<String, String> upkeep;
	private Map<String, String> production;
	private Map<String, Map<String, String>> districtAvailableList;
	private Map<VarName, Object> vars;
	private static Map<String, String> districtTypeList;
	
	public District(String t, Set<String> tags) {
		init(tags);
		init(t);
		init();
	}

	public District(Set<String> tags) {
		init(tags);
		init();
	}
	
	private void init() {
		
		vars = new EnumMap<VarName, Object>(VarName.class);
		
		vars.put(VarName.NAME, type);
		vars.put(VarName.ICON, VariableArray.getVar("icons\\district\\" + type));
		
	}
	
	private void init(Set<String> tags) {
		
		districtAvailableList = new HashMap<String, Map<String, String>>();
		Map<String, String> potential = Other.getAllMatchingKeys(districtTypeList, ".*\\.potential\\..*", 0);
		
		for(Entry<String, String> e: potential.entrySet()) {
			
			switch(e.getKey().split("\\.")[2]) {
				
				case "has_tag":
					
					if(tags.contains(e.getValue())) {
						districtAvailableList.putIfAbsent(e.getKey().split("\\.")[0], Other.getAllMatchingKeys(districtTypeList, e.getKey().split("\\.")[0], 1));
					}
					
					break;
					
			}
			
		}
		
	}
	
	private void init(String t) {
		
		Map<String, String> d = districtAvailableList.get(t);
		baseBuildTime = Math.toIntExact(Math.round(Double.parseDouble(d.get("base_buildtime"))));
		type = t;
		
		cost = Other.getAllMatchingKeys(d, "resources\\.cost\\..*", 2);
		upkeep = Other.getAllMatchingKeys(d, "resources\\.upkeep\\..*", 2);
		production = Other.getAllMatchingKeys(d, "resources\\.production\\..*", 2);
		
	}
	
	public Map<VarName, Object> getVars() {
		return vars;
	}
	
	public String toString() {
		return type;
	}
	
	public static List<String> getPotentialDistricts(Set<String> tags) {

		List<String> potential = new ArrayList<String>();
		Map<String, String> hasTags = Other.getAllMatchingKeys(districtTypeList, ".*\\.potential\\.has_tag", 0);
		
		for(Entry<String, String> e: hasTags.entrySet()) {
			
			if(tags.contains(e.getValue())) {
				String t = e.getKey().split("\\.")[0];
				potential.add(t);
			}
			
		}
		
		return potential;
		
	}
	
	public static void setDistrictTypeList(Map<String, String> d) {
		districtTypeList = d;
	}

}
