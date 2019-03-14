package species.colony;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import files.type.Type;
import files.type.TypeBuilding;
import files.type.TypeDistrict;
import files.type.TypeJob;
import files.type.TypeLoader;
import species.Species;
import species.colony.build.BuildQueue;
import species.colony.build.BuildQueueEntry;
import species.colony.build.Building;
import species.colony.build.District;

public class ResourceManager {
	
	private float lastUpdate;
	
	private ArrayList<TypeDistrict> districtTypes;
	private ArrayList<TypeBuilding> buildingTypes;
	private ArrayList<Pop> popList;
	private ArrayList<ResourceManager> childManagers;

	private HashMap<String, ArrayList<Pop>> unemployedPops;
	private HashMap<TypeJob, ArrayList<Job>> jobList;
	private HashMap<String, Integer> jobIndex;
	private HashMap<String, TypeJob> jobTypes;
	private HashMap<String, Double> resourceIncome;
	private HashMap<String, Double> resource;
	
	private TypeLoader colonyLoader;
	private Species species;
	private BuildQueue buildQueue;
	
	private District[] districts;
	private Building[] buildings;
	
	/**
	 * initalizes ResourceManager
	 * @param cl 
	 * 
	 * @param species the species the resourcemanager is linked to
	 */
	public ResourceManager(Species s, TypeLoader cl, HashMap<String, Double> r) {
		
		lastUpdate = 0;
		
		resource = r;
		species = s;
		colonyLoader = cl;
		
		buildQueue = new BuildQueue();
		
		popList = new ArrayList<Pop>();
		childManagers = new ArrayList<ResourceManager>();
		unemployedPops = new HashMap<String, ArrayList<Pop>>();
		jobIndex = new HashMap<String, Integer>();
		resourceIncome = new HashMap<String, Double>();
		jobTypes = new HashMap<String, TypeJob>();
		jobList = new HashMap<TypeJob, ArrayList<Job>>();
		
		districts = new District[1] ;
		buildings = new Building[1] ;
		
		for(int i = 0; i < 50; i++) {
			
			Pop p = new Pop(species.getDefaltPopCategory());
			popList.add(p);
			
			ArrayList<Pop> list = unemployedPops.getOrDefault(p.getRank().getName(), new ArrayList<Pop>());
			list.add(p);
			
			unemployedPops.putIfAbsent(p.getRank().getName(), list);
			
		}
		
		for(Type j: colonyLoader.getTypes("job")) {
			jobTypes.put(j.getName(), (TypeJob) j);
		}
		
		species.add(this); //adds the resorse manager for the colony to the species
		
	}
	
	/**
	 * adds a district to the manager
	 */
	public void set(District[] d) {
		districts = d;
	}
	public void set(Building[] b) {
		buildings = b;
	}

	public void setDistricts(ArrayList<TypeDistrict> d) {
		districtTypes = d;
	}

	public void setBuildings(ArrayList<TypeBuilding> d) {
		buildingTypes = d;
	}

	public ArrayList<TypeDistrict> getDistrictTypes() {
		return districtTypes;
	}

	public ArrayList<TypeBuilding> getBuildingTypes() {
		return buildingTypes;
	}

	public ArrayList<District> getDistricts() {
		
		ArrayList<District> t = new ArrayList<District>();
		
		for(District d: districts)
			t.add(d);
		
		return t;
		
	}

	public ArrayList<Building> getBuildings() {
		
		ArrayList<Building> t = new ArrayList<Building>();
		
		for(Building d: buildings)
			t.add(d);
		
		return t;
		
	}
	
	/**
	 * updates the resourcemanager
	 * @param update 
	 */
	public void update(int update) {
		buildQueue.update(update);
		updateIncome();
	}

	public int getRemainingDistricts() {
		return 1;
	}

	public void addBuildQueue(BuildQueueEntry b) {
		buildQueue.add(b);
	}
	
	public float getLastUpdate() {
		return lastUpdate;
	}
	
	public ArrayList<Pop> getPopList(){
		return popList;
	}
	
	public HashMap<String, ArrayList<Pop>> getUnemploymentList(){
		return unemployedPops;
	}
	
	public HashMap<TypeJob, ArrayList<Job>> getJobList(){
		return jobList;
	}

	public void updateIncome() {
		
		resourceIncome.clear(); //clears the current resorce list
		
		HashMap<String, Integer> jobChange = new HashMap<String, Integer>(jobIndex);
		
		for(District d: districts) {
			
			if(d == null)
				continue;
			for(Entry<String, Double> p: d.getTotalProduction().entrySet()) {
			
				if(p.getKey().startsWith("job_")) {
					jobChange.put(p.getKey().replaceFirst("job_", ""), (int) (jobChange.getOrDefault(p.getKey().replaceFirst("job_", ""), 0)-p.getValue()));
				} else {
					//adds to the preveus amount
					Double amount = resourceIncome.get(p.getKey());
					if(amount == null)
						resourceIncome.put(p.getKey(), p.getValue());
					else
						resourceIncome.put(p.getKey(), p.getValue()+amount);
				}
			
			}
			
		}
		
		for(Building d: buildings) {
			
			if(d == null)
				continue;
			for(Entry<String, Double> p: d.getTotalProduction().entrySet()) {
			
				if(p.getKey().startsWith("job_")) {
					jobChange.put(p.getKey().replaceFirst("job_", ""), (int) (jobChange.getOrDefault(p.getKey().replaceFirst("job_", ""), 0)-p.getValue()));
				} else {
					//adds to the preveus amount
					Double amount = resourceIncome.get(p.getKey());
					if(amount == null)
						resourceIncome.put(p.getKey(), p.getValue());
					else
						resourceIncome.put(p.getKey(), p.getValue()+amount);
				}
			
			}
			
		}
		
		for(Entry<String, Integer> c: jobChange.entrySet()) {
			
			int delta = c.getValue();
			boolean change = delta < 0;
			
			ArrayList<Job> j = jobList.getOrDefault(jobTypes.get(c.getKey()), new ArrayList<Job>());
			if(jobList.get(jobTypes.get(c.getKey())) == null) {
				jobList.put(jobTypes.get(c.getKey()), j);
			}
			
			for(int i = 0; i < Math.abs(delta); i++) {
					if(change) {
						TypeJob t = jobTypes.get(c.getKey());
						Job newJob = new Job(t);
						j.add(newJob);
						if(unemployedPops.get(t.getCatagory()) != null && !unemployedPops.get(t.getCatagory()).isEmpty()) {
							ArrayList<Pop> l = unemployedPops.get(t.getCatagory());
							Pop pop = l.get(0);
							l.remove(pop);
							newJob.setPop(pop);
						}
					} else {
						Job f = j.get(0);
						unemployedPops.get(f.getPop().getRank().getName()).add(f.firePop());
						j.remove(0);
					}
			}
			
			jobIndex.put(c.getKey(), jobIndex.getOrDefault(c.getKey(), 0)-c.getValue());
			
			if(jobIndex.containsKey(c.getKey()) && jobIndex.get(c.getKey()) <= 0) {
				jobIndex.remove(c.getKey());
				jobList.remove(jobTypes.get(c.getKey()));
			}
			
		}
		
		for(ArrayList<Job> jobs: jobList.values()) {
			for(Job j: jobs) {
				if(j.getPop() != null) {
					for(Entry<String, Double> p: j.getProduction().entrySet()) {
						//adds to the preveus amount
						Double amount = resourceIncome.get(p.getKey());
						if(amount == null)
							resourceIncome.put(p.getKey(), p.getValue());
						else
							resourceIncome.put(p.getKey(), p.getValue()+amount);
					}
				}
			}
		}
		
		//loops through all districts and tally up the total
//		for(District d: districts) {
//			if(d == null) //if a district is not set
//				continue;
//			for(Entry<String, Double> p: d.getTotalProduction().entrySet()) {
//				
//				if(p.getKey().startsWith("job") && !unemployedPops.isEmpty()) {
//					for(double i = 0; i < p.getValue(); i++) {
//						Pop pop = unemployedPops.get(0);
//						unemployedPops.remove(pop);
//						jobList.add(new Job(jobTypes.get(p.getKey().replace("job_", "")), pop));
//					}
//				} else {
//					//adds to the preveus amount
//					Double amount = resources.get(p.getKey());
//					if(amount == null)
//						resources.put(p.getKey(), p.getValue());
//					else
//						resources.put(p.getKey(), p.getValue()+amount);
//				}
//				
//			}
//		}
		
//		for(Building d: buildings) {
//			if(d == null) //if a district is not set
//				continue;
//			for(Entry<String, Double> p: d.getTotalProduction().entrySet()) {
//				
//				if(p.getKey().startsWith("job") && !unemployedPops.isEmpty()) {
//					for(double i = 0; i < p.getValue(); i++) {
//						Pop pop = unemployedPops.get(0);
//						unemployedPops.remove(pop);
//						jobList.add(new Job(jobTypes.get(p.getKey().replace("job_", "")), pop));
//					}
//				} else {
//					//adds to the preveus amount
//					Double amount = resources.get(p.getKey());
//					if(amount == null)
//						resources.put(p.getKey(), p.getValue());
//					else
//						resources.put(p.getKey(), p.getValue()+amount);
//				}
//				
//			}
//		}
		
//		for(Job j: jobList) {
//			for(Entry<String, Double> p: j.getProduction().entrySet()) {
//				//adds to the preveus amount
//				Double amount = resources.get(p.getKey());
//				if(amount == null)
//					resources.put(p.getKey(), p.getValue());
//				else
//					resources.put(p.getKey(), p.getValue()+amount);
//				
//			}
//		}
		
		lastUpdate = System.nanoTime();
		
	}
	
	public HashMap<String, Double> getResourceIncome() {
		return new HashMap<String, Double>(resourceIncome);
	}
	
	public HashMap<String, Double> getResourceTotal() {
		return new HashMap<String, Double>(resource);
	}

	public BuildQueue getBuildQueue() {
		return buildQueue;
	}

	public ResourceManager createChildManager() {
		ResourceManager child = new ResourceManager(species, colonyLoader, species.getResourceManagerMaster().getResourceTotal());
		childManagers.add(child);
		return child;
	}
	
}
