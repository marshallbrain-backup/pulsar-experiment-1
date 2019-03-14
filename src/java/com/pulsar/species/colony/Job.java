package species.colony;

import java.util.HashMap;
import java.util.Map.Entry;

import files.type.TypeJob;

public class Job {
	
	private String type;
	
	private HashMap<String, Double> production;
	
	private TypeJob jobType;
	private Pop pop;

	public Job(TypeJob t, Pop p) {
		pop = p;
	}
	
	public Job(TypeJob t) {
		inti(t);
	}
	
	private void inti(TypeJob jt) {

		jobType = jt;
		
		production = new HashMap<String, Double>();
		
		type = jobType.getName();
		
		for(Entry<String, String> j: jobType.getProduction().entrySet()) {
			production.put(j.getKey().split("\\.")[2], Double.parseDouble(j.getValue()));
		}
		
	}

	public HashMap<String, Double> getProduction() {
		return new HashMap<String, Double>(production);
	}
	
	public Pop getPop() {
		return pop;
	}
	
	public boolean equals(Job j) {
		for(String s: j.getProduction().keySet()) {
			if(!production.containsKey(s)) {
				return false;
			}
		}
		return true;
	}

	public String getType() {
		return type;
	}

	public Pop firePop() {
		Pop p = pop;
		pop = null;
		return p;
	}
	
	public String toString() {
		return type;
	}

	public void setPop(Pop p) {
		pop = p;
	}

}
