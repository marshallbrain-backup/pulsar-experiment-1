package species.colony;

import java.util.ArrayList;

import bodys.Body;
import files.Type;
import files.type.TypeBuilding;
import files.type.TypeDistrict;
import files.type.TypeLoader;
import files.type.TypePlanetaryProjects;
import species.Species;
import species.colony.build.Building;
import species.colony.build.District;

public class Colony {
	
	private District[] districts; //array for all posible district slots
	private Building[] buildings;
	
	private ResourceManager resource;
	private Body body;
	
	/**
	 * initalizes  colony
	 * 
	 * @param planet the planet that the colony is on
	 * @param species the species that the colony is under controle by, relevent for species boneses
	 * @param gamefile the gamefile containing district info
	 */
	public Colony(Body b, Species s, TypeLoader colonyLoader) {
		
		body = b;
		
		resource = new ResourceManager(s, colonyLoader, s.getResourceManagerMaster().getResourceTotal());
		
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

		ArrayList<Type> bt = colonyLoader.getTypes("building");
		ArrayList<TypeBuilding> buildingTypes = new ArrayList<TypeBuilding>();
		buildings = new Building[44];
		
		resource.set(buildings);
		
		for(int i = 0; i < buildings.length; i++) {
			buildings[i] = new Building(resource);
		}
		
		for(int i = 0; i < bt.size(); i++) {
			if(((TypeBuilding) bt.get(i)).isPotential(b)) {
				buildingTypes.add((TypeBuilding) bt.get(i));
			}
		}
		
		resource.setBuildings(buildingTypes);

		ArrayList<Type> pt = colonyLoader.getTypes("planetaryProjects");
		ArrayList<TypePlanetaryProjects> projectTypes = new ArrayList<TypePlanetaryProjects>();
		
		for(int i = 0; i < pt.size(); i++) {
			if(((TypePlanetaryProjects) pt.get(i)).isPotential(b)) {
				projectTypes.add((TypePlanetaryProjects) pt.get(i));
			}
		}
		
		resource.setProject(projectTypes);
		
	}

	public ResourceManager getResourceManager() {
		return resource;
	}

	public Body getBody() {
		return body;
	}

}
