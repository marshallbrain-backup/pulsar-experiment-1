package files.type;

import files.GameFile;

public class TypeSystem extends Type{

	public TypeSystem(GameFile f) {
		super(f);
		
		if(!checkFile(gameFile)) {
			System.out.println("TypeSystem Failed");
			System.exit(0);
		}
		
	}

	/**
	 * checks requiered information in the gamefile. If something does not exist than abort the program
	 * 
	 * @param gameFile the gamefile being checked
	 */
	private boolean checkFile(GameFile f) {
		
		if(!isString(f.getField("star.key")))		return false;

		if(!isInt(f.getField("spawn_odds")))		return false;
		if(!isInt(f.getField("num_planets.min")))	return false;
		if(!isInt(f.getField("num_planets.max")))	return false;
		
		return true;
		
	}

	public String getStarClasses() {
		return gameFile.getField("star.key");
	}
	
	/**
	 * gets a random number of planets for the type
	 */
	public int getPlanetNumber() {
		
		int min = Integer.parseInt(gameFile.getField("num_planets.min"));
		int max = Integer.parseInt(gameFile.getField("num_planets.max"));
		
		return min + r.nextInt(max-min+1);
		
	}

}
