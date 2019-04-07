package universe;

import java.util.List;
import java.util.Map;
import java.util.Random;

import files.GameFile;
import species.Species;

public class Galaxy {
	
	private StarSystem starSystem;

	/**
	 * initalizes Galaxy
	 * 
	 * @param gamefile gamefile
	 * @param species all speties starting in the galaxy
	 * @param systems 
	 * @param keyboard keyboard class
	 * @param mouse mouse class
	 */
	public Galaxy(Species sp, List<Map<String, String>> sy, GameFile b) {
		
		init(sp, sy, b);
		
	}

	/**
	 * initalization for galaxy
	 */
	private void init(Species sp, List<Map<String, String>> sy, GameFile b) {
		
		int totalSystems = 1; //max systems in galaxy
		
		Random r = new Random();
		
		//loop for number of systems
		for(int i = 0; i < totalSystems; i++) {
			starSystem = new StarSystem(b, sy.get(r.nextInt(sy.size())), sp); //make a new system based on the type
		}
		
	}
	
	public StarSystem getStarSystem() {
		return starSystem;
	}

	/**
	 * tick
	 * @param m 
	 * @param k 
	 */
	public void tick() {
	}

}
