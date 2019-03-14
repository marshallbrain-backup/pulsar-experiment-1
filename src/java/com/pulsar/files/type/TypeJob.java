package files.type;

import java.util.HashMap;

import files.GameFile;

public class TypeJob extends Type {

	public TypeJob(GameFile f) {
		super(f);
		
		if(!checkFile(f)) {
			System.out.println("DistrictType Failed");
			System.exit(0);
		}
		
	}

	/**
	 * checks requiered information in the gamefile. If something does not exist than abort the program
	 * 
	 * @param gameFile the gamefile being checked
	 */
	private boolean checkFile(GameFile f) {
		
		if(!isInt(f.getFieldOne("resources.production")))	return false;
		
		return true;
		
	}
	
	public HashMap<String, String> getProduction() {
		return gameFile.getFieldAll("resources.production");
	}

	public String getCatagory() {
		return gameFile.getField("category");
	}

}
