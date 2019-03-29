package universe;

import java.util.List;
import java.util.Map;

import files.GameFile;
import species.Species;

public class Universe {
	
	private Galaxy galaxy;
	
	private Species species;
	private Clock clock;
	
	/**
	 * initalizes Universe
	 * 
	 * @param save the save game file
	 * @param keyboard keyboard class
	 * @param mouse mouse class
	 * @param gamefile gamefile
	 */
	public Universe(String save, GameFile gf) {
		
		init(gf);
		
	}
	
	/**
	 * initalization for universe
	 */
	private void init(GameFile gf) {
		
//		species = new Species(gf);
		
		List<Map<String, String>> systems = GameFile.convertFiles(gf.getFieldAll("system_classes\\..*", 1));
		GameFile bodyTypes = new GameFile(gf.getFieldAll("body_classes\\..*", 1));
		
		galaxy = new Galaxy(species, systems, bodyTypes);
		clock = new Clock();
		
	}
	
	public Galaxy getGalaxy() {
		return galaxy;
	}
	
	//TODO make update var a class to get total time and days
	/**
	 * tick meathod
	 * @param m 
	 * @param k 
	 */
	public void tick() {
		
		long update = clock.tick();
		
		galaxy.tick();
//		species.update(update);
		
	}

}
