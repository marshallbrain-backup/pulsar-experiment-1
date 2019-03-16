package species;

import java.util.ArrayList;

import bodys.Body;
import bodys.Planet;
import files.type.TypeCategory;
import files.type.TypeLoader;
import fleets.Ship;
import species.colony.Colony;
import species.colony.ResourceManager;
import species.colony.ResourceManagerMaster;
import species.colony.Station;

public class Species {
	
	private long timeUpdate;
	private long timePerUpdate;
	
	private ArrayList<Body> colonys = new ArrayList<Body>(); //list of colonys that the species controles
	
	private TypeLoader speciesLoader;
	private ResourceManagerMaster master; //the master resourcemanager for the entier species
	private Ship ship;
	
	/**
	 * initalizes species
	 * 
	 * @param gamefile gamefile
	 */
	public Species(TypeLoader sl) {
		
		timePerUpdate = 86400;
		timeUpdate = 0;
		
		speciesLoader = sl;
		
		master = new ResourceManagerMaster(this, speciesLoader);
		
		ship = new Ship(this);
		
	}
	
	/**
	 * creates a colony for this species
	 * 
	 * @param planet the planet that the colony is on
	 * @return colony the colony that was created
	 */
	public Colony createColony(Body b) {
		
		if(b.getColonyType() == null)
			return null;
		
		Colony c = new Colony(b, this, speciesLoader);
		
		b.addColony(c);
		colonys.add(b);
		ship.setSystem(b.getSystem());
		
		return c;
		
	}

	public Station createStation(Body b) {
		
		Colony c = b.getColony();
		Station s = null;
		
		if(c == null) {
			s = new Station(b, this, speciesLoader);
		} else {
			s = new Station(b, speciesLoader);
		}
		
		if(s != null) {
			b.addStation(s);
			colonys.add(b);
		}
		
		return s;
		
	}
	
	/**
	 * adds a resourcemanager to the speses. alows updating all resorses through one class
	 * 
	 * @param resourcemanager the resorse manager of a colony to add to the speses
	 */
	public void add(ResourceManager r) {
		master.add(r);
	}
	
	/**
	 * updates every thing related to a given species
	 * @param update 
	 */
	public void update(long update) {
		
		timeUpdate += update;
		if(timeUpdate >= timePerUpdate) {
			
			int updateNum = (int) Math.floorDiv(timeUpdate, timePerUpdate);
			timeUpdate = 0;
			master.update(updateNum);
			
		}
		
		ship.update(update);
		
	}

	public TypeCategory getDefaltPopCategory() {
		return master.getDefaltPopCategory();
	}

	public ResourceManagerMaster getResourceManagerMaster() {
		return master;
	}

}
