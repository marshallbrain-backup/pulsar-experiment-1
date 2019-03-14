package species.colony.build;

import java.util.HashMap;
import java.util.Map.Entry;

import files.type.TypeDistrict;
import species.colony.ResourceManager;

public class District implements Buildable {
	
	private int quantaty;
	private int buildCost;
	
	private HashMap<String, Double> cost;
	private HashMap<String, Double> upkeep;
	private HashMap<String, Double> production;
	
	private ResourceManager resource;
	private TypeDistrict districtType;
	private TypeDistrict pendingType;

	/**
	 * initalize planet
	 * 
	 * @param districtType the type of district
	 * @param resourceManager the resourceManager, probely does not need it
	 */
	public District(ResourceManager r) {
		
		resource = r;
		
		init();
		
	}
	
	private void init() {
		
		cost = new HashMap<String, Double>();
		upkeep = new HashMap<String, Double>();
		production = new HashMap<String, Double>();
		
		quantaty = 0;
		buildCost = 0;
		
	}

	public void set(TypeDistrict d) {
		
		districtType = d;
		
		init();
		
		//sets production to be what the district should produce
		for(Entry<String, String> p: districtType.getProduction().entrySet()) {
			production.put(p.getKey().split("\\.")[2], Double.parseDouble(p.getValue()));
		}
		
	}

	@Override
	public void buildAction(BuildActionType a) {
		
		if(a == BuildActionType.BUILD) {
			buildInstint();
		} else if(a == BuildActionType.REMOVE) {
			removeInstint();
		} else if(a == BuildActionType.REPLACE) {
			retoolInstint();
		}
		
	}
	
	public boolean build() {
		if(tryBuild()) {
			resource.addBuildQueue(new BuildQueueEntry(this, BuildActionType.BUILD, buildCost));
			return true;
		}
		return false;
	}
	
	public void buildInstint() {
		if(tryBuild()) {
			quantaty += 1;
			resource.updateIncome();
		}
	}
	
	public boolean tryBuild() {
		if(resource.getRemainingDistricts() > 0) {
			return true;
		}
		return false;
	}
	
	public boolean remove() {
		if(tryRemove()) {
			resource.addBuildQueue(new BuildQueueEntry(this, BuildActionType.REMOVE, buildCost));
			return true;
		}
		return false;
	}
	
	public void removeInstint() {
		if(tryRemove()) {
			quantaty -= 1;
			resource.updateIncome();
		}
	}
	
	public boolean tryRemove() {
		if(quantaty > 0) {
			return true;
		}
		return false;
	}

	public void replace(District d) {
		if(tryRemove()) {
			if(d.tryBuild()) {
				resource.addBuildQueue(new BuildQueueEntry(d, BuildActionType.BUILD, this, BuildActionType.REMOVE, buildCost));
			}
		}
	}

	/**
	 * @return base production of the district
	 */
	public HashMap<String, Double> getTotalProduction() {
		
		HashMap<String, Double> totalProduction = new HashMap<String, Double>();
		
		for(Entry<String, Double> p: production.entrySet()) {
			totalProduction.put(p.getKey(), p.getValue()*quantaty);
		}
		
		return totalProduction;
		
	}

	public String getName() {
		return districtType.getName();
	}

	public TypeDistrict getType() {
		return districtType;
	}

	public TypeDistrict getPendingType() {
		return pendingType;
	}
	
	public String toString() {
		return districtType.getName();
	}

	public ResourceManager getResourceManager() {
		return resource;
	}

	public int getQuantaty() {
		return quantaty;
	}

	public int getMaxQuantaty() {
		return resource.getRemainingDistricts() + quantaty;
	}

	public void retool(TypeDistrict t) {
		resource.addBuildQueue(new BuildQueueEntry(this, BuildActionType.REPLACE, buildCost));
		pendingType = t;
	}

	public void retoolInstint() {
		set(pendingType);
	}

}
