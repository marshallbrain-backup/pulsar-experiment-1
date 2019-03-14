package species.colony;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import files.type.Type;
import files.type.TypeCategory;
import files.type.TypeLoader;
import species.Species;

public class ResourceManagerMaster {
	
	private ArrayList<ResourceManager> managerList;
	
	private HashMap<String, TypeCategory> popCategory;
	private HashMap<String, Double> resourceIncome;
	private HashMap<String, Double> resource;
	
	private TypeCategory defaltPopCategory;
	private Species species;

	public ResourceManagerMaster(Species s ,TypeLoader l) {
		
		species = s;
		
		resourceIncome = new HashMap<String, Double>();
		resource = new HashMap<String, Double>();
		managerList = new ArrayList<ResourceManager>();
		popCategory = new HashMap<String, TypeCategory>();
		
		ArrayList<Type> c = l.getTypes("category");
		
		for(Type t: c) {
			TypeCategory tc = (TypeCategory) t;
			popCategory.put(t.getName(), tc);
			if(tc.isDefalt(species)) {
				defaltPopCategory = tc;
			}
		}
		
	}

	public void add(ResourceManager r) {
		managerList.add(r);
	}

	public void update(int update) {
		
		resourceIncome.clear();
		
		for(ResourceManager r: managerList) {
			r.update(update);
			for(Entry<String, Double> e: r.getResourceIncome().entrySet()) {
				Double a = resourceIncome.getOrDefault(e.getKey(), 0.0);
				a += e.getValue();
				resourceIncome.put(e.getKey(), a);
			}
		}
		
		for(Entry<String, Double> e: resourceIncome.entrySet()) {
			Double a = resource.getOrDefault(e.getKey(), 0.0);
			a += e.getValue();
			resource.put(e.getKey(), a);
		}
		
	}

	public TypeCategory getDefaltPopCategory() {
		return defaltPopCategory;
	}

	public HashMap<String, Double> getResourceTotal() {
		return resource;
	}

	public HashMap<String, Double> getResourceIncome() {
		return resourceIncome;
	}

}
