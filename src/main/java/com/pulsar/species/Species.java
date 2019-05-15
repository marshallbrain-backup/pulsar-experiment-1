package species;

import bodys.Body;
import files.GameFile;
import species.colony.Colony;
import species.colony.ResourceManagerMaster;

public class Species {
	
	private long timeUpdate;
	private long timePerUpdate;
	
	private Colony colony;
	private GameFile gameFile;
	private ResourceManagerMaster master; //the master resourcemanager for the entier species
	
	/**
	 * initalizes species
	 * 
	 * @param gamefile gamefile
	 */
	public Species(GameFile gf) {
		
		timePerUpdate = 86400;
		timeUpdate = 0;
		
		gameFile = gf;
		
	}
	
	/**
	 * updates every thing related to a given species
	 * @param update 
	 */
	public void update(long update) {
		
		timeUpdate += update;
		if(timeUpdate >= timePerUpdate) {
			
			int updateNum = (int) Math.floorDiv(timeUpdate, timePerUpdate);
			timeUpdate -= timePerUpdate;
			
		}
		
	}
	
	public void createColony(Body b) {
		
		colony = new Colony(b, this);
		
	}

}
