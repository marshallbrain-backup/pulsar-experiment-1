package files.type;

import files.GameFile;

public class TypePlanet extends Type {

	public TypePlanet(GameFile f) {
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
		
		if(!isString(f.getField("climate")))			return false;

		if(!isInt(f.getField("color")))					return false;
		if(!isInt(f.getField("entity_scale")))			return false;
		if(!isInt(f.getField("distance_from_sun.min")))	return false;
		if(!isInt(f.getField("distance_from_sun.max")))	return false;
		if(!isInt(f.getField("planet_size.min")))		return false;
		if(!isInt(f.getField("planet_size.max")))		return false;
		if(!isInt(f.getField("moon_size.min")))			return false;
		if(!isInt(f.getField("moon_size.max")))			return false;
		
		if(!isDouble(f.getField("spawn_odds")))			return false;
		if(!isDouble(f.getField("chance_of_ring")))		return false;
		
		return true;
		
	}

	/**
	 * gets size of planet based on type
	 */
	public int getSize() {
		
		int min = Integer.parseInt(gameFile.getField("planet_size.min"));
		int max = Integer.parseInt(gameFile.getField("planet_size.max"));
		
		return min + r.nextInt(max-min+1);
	}

	public int getColor() {
		return Integer.parseInt(gameFile.getField("color"));
	}

	public double getEntityScale() {
		return Double.parseDouble(gameFile.getField("entity_scale"));
	}

	public String colonyType() {
		return gameFile.getField("planet_catagory");
	}

	public boolean isColonizable() {
		String b = gameFile.getField("colonizable");
		if(b == null || b.equalsIgnoreCase("false"))
			return false;
		return true;
	}

}
