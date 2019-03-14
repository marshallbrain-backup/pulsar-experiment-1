package files.type;

import files.GameFile;

public class TypeStar extends Type{

	public TypeStar(GameFile f) {
		super(f);
		
		if(!checkFile(gameFile)) {
			System.out.println("TypeStar Failed");
			System.exit(0);
		}
		
	}

	/**
	 * checks requiered information in the gamefile. If something does not exist than abort the program
	 * 
	 * @param gameFile the gamefile being checked
	 */
	private boolean checkFile(GameFile f) {
		
		if(!isInt(f.getField("color")))				return false;
		if(!isInt(f.getField("star_size.min")))		return false;
		if(!isInt(f.getField("star_size.max")))		return false;

		if(!isDouble(f.getField("entity_scale")))	return false;
		
		if(!isBoolean(f.getField("colonizable")))	return false;
		
		return true;
		
	}

	public int getColor() {
		return Integer.parseInt(gameFile.getField("color"));
	}

	public double getEntityScale() {
		return Double.parseDouble(gameFile.getField("entity_scale"));
	}

	/**
	 * gets size of star based on type
	 */
	public int getSize() {
		
		int min = Integer.parseInt(gameFile.getField("star_size.min"));
		int max = Integer.parseInt(gameFile.getField("star_size.max"));
		
		return min + r.nextInt(max-min+1);
		
	}

}
