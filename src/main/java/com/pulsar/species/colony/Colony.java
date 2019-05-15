package species.colony;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bodys.Body;
import files.VarInterface;
import files.VarName;
import species.Species;
import species.colony.buildable.District;

public class Colony implements VarInterface {
	
	private Body body;
	
	private District[] districts;
	
	private Map<String, Map<VarName, Object>> vars;
	
	public Colony(Body b, Species s) {
		
		body = b;
		
		body.setColony(this); 
		
		init();
		
	}
	
	private void init() {
		
		vars = new HashMap<String, Map<VarName, Object>>();
		
		initDistricts();
		
	}

	private void initDistricts() {
		
		districts = new District[4];
		
		List<String> potential = District.getPotentialDistricts(body.getTags());
		
		for(int i = 0; i < districts.length; i++) {
			if(i < potential.size()) {
				districts[i] = new District(potential.get(i), body.getTags());
			} else {
				districts[i] = new District(body.getTags());
			}
			vars.put("district"+String.valueOf(i), districts[i].getVars());
		}
		
	}

	@Override
	public Map<String, Map<VarName, Object>> getVarList() {
		return vars;
	}

}
