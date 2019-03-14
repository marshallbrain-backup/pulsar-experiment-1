package species.colony.build;

import java.util.HashMap;
import java.util.Map.Entry;

import files.type.TypeBuilding;
import species.colony.ResourceManager;

public class Building implements Buildable {
	
	private int buildCost;
	
	private HashMap<String, Double> cost;
	private HashMap<String, Double> upkeep;
	private HashMap<String, Double> production;
	
	private ResourceManager resource;
	private TypeBuilding buildingType;
	private TypeBuilding pendingType;

	/**
	 * initalize planet
	 * 
	 * @param districtType the type of district
	 * @param resourceManager the resourceManager, probely does not need it
	 */
	public Building(ResourceManager r) {
		
		resource = r;
		
		init();
		
	}
	
	private void init() {
		
		cost = new HashMap<String, Double>();
		upkeep = new HashMap<String, Double>();
		production = new HashMap<String, Double>();
		
		buildCost = 0;
		
	}

	public TypeBuilding getType() {
		return buildingType;
	}

	public ResourceManager getResourceManager() {
		return resource;
	}

	@Override
	public void buildAction(BuildActionType a) {
		
		if(a == BuildActionType.BUILD) {
			buildInstint();
		} else if(a == BuildActionType.REMOVE) {
//			removeInstint();
		} else if(a == BuildActionType.REPLACE) {
//			retoolInstint();
		}
		
	}

	public void build(TypeBuilding t) {
		resource.addBuildQueue(new BuildQueueEntry(this, BuildActionType.BUILD, buildCost));
		pendingType = t;
	}

	public void buildInstint() {
		set(pendingType);
		resource.updateIncome();
	}
	
	public boolean tryBuild() {
		return true;
	}

	public void set(TypeBuilding b) {
		
		buildingType = b;
		
		init();
		
		//sets production to be what the district should produce
		for(Entry<String, String> p: buildingType.getProduction().entrySet()) {
			production.put(p.getKey().split("\\.")[2], Double.parseDouble(p.getValue()));
		}
		
	}
	
	public HashMap<String, Double> getTotalProduction() {		
		return production;		
	}

	public String getTooltip() {
		if(getType() == null)
			return "null_tooltip";
		return getType().getName()+"_tooltip";
	}

}