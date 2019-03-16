package files.type;

import java.util.HashMap;

import bodys.Body;
import files.GameFile;

public class TypePlanetaryProjects extends Type {

	public TypePlanetaryProjects(GameFile f) {
		super(f);
		
		if(!checkFile(gameFile)) {
			System.out.println("TypePlanet Failed");
			System.exit(0);
		}
		
	}

	/**
	 * checks requiered information in the gamefile. If something does not exist than abort the program
	 * 
	 * @param gameFile the gamefile being checked
	 */
	private boolean checkFile(GameFile f) {
		
		return true;
		
	}
	
	//TODO create scripting handler
	public boolean isPotential(Body b) {
		
		HashMap<String, String> potential = gameFile.getFieldAll("potential");
		for(String k: potential.keySet()) {
			if(k.equals("potential.is_owned")) {
				if(potential.get(k).equals("true")) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	public boolean isAllow(Body b) {
		
		HashMap<String, String> potential = gameFile.getFieldAll("potential");
		for(String k: potential.keySet()) {
			if(k.equals("potential.can_colony_suport")) {
				if(potential.get(k).equals(String.valueOf(b.tryAddStation()))) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	public String getEffect() {
		return gameFile.getField("effect.build_station");
	}
	
	public HashMap<String, String> getCost() {
		return gameFile.getFieldAll("resources.cost");
	}

}
