package universe;

import java.util.List;
import java.util.Map;
import java.util.Random;

import files.GameFile;
import species.Species;

public class Galaxy {
	
	private StarSystem s;

	/**
	 * initalizes Galaxy
	 * 
	 * @param gamefile gamefile
	 * @param species all speties starting in the galaxy
	 * @param keyboard keyboard class
	 * @param mouse mouse class
	 */
	public Galaxy(Species species, GameFile gf) {
		
		init(species, gf);
		
	}

	/**
	 * initalization for galaxy
	 */
	private void init(Species species, GameFile gf) {
		
		int totalSystems = 1; //max systems in galaxy
		
		Random r = new Random();
		List<Map<String, String>> systems = GameFile.convertFiles(gf.getFieldAll("system_classes\\..*"));
		
		//loop for number of systems
		for(int i = 0; i < totalSystems; i++) {
			s = new StarSystem(gf, systems.get(r.nextInt(systems.size())), species); //make a new system based on the type
		}
		
	}

	/**
	 * tick
	 * @param m 
	 * @param k 
	 */
	public void tick() {
	}

}
