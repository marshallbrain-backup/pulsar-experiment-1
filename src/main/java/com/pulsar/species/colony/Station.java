package species.colony;

import java.util.ArrayList;

import bodys.Body;
import files.Type;
import files.type.TypeDistrict;
import files.type.TypeLoader;
import species.Species;
import species.colony.build.District;

//TODO make buildable via a planetary project
public class Station {
	
	private District[] districts; //array for all posible district slots
	
	private ResourceManager resource;

	public Station(Body b, Species s, TypeLoader colonyLoader) {
		resource = new ResourceManager(s, colonyLoader, s.getResourceManagerMaster().getResourceTotal());
		init(b, colonyLoader);
	}

	public Station(Body b, TypeLoader colonyLoader) {
		resource = b.getColony().getResourceManager().createChildManager();
		init(b, colonyLoader);
	}
	
	private void init(Body b, TypeLoader colonyLoader) {
		
		//get type array contaning typedistricts with information from the district folder
		ArrayList<Type> dt = colonyLoader.getTypes("district");
		ArrayList<TypeDistrict> districtTypes = new ArrayList<TypeDistrict>();
		districts = new District[8];
		
		resource.set(districts); //adds the district array to the resorcemanager
		
		for(int i = 0; i < districts.length; i++) {
			districts[i] = new District(resource);
		}
		
		//loops through all district
		for(int i = 0; i < dt.size(); i++) {
			
			if(((TypeDistrict) dt.get(i)).isPotential(b)) {
				districtTypes.add((TypeDistrict) dt.get(i));
			}
			
		}
		
		resource.setDistricts(districtTypes);
		
	}

	public ResourceManager getResourceManager() {
		return resource;
	}

}
