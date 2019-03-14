package files.type;

import java.util.HashMap;

import files.GameFile;
import species.Species;

public class TypeCategory extends Type{

	public TypeCategory(GameFile f) {
		super(f);
		
		if(!checkFile(f)) {
			System.out.println("CategoryType Failed");
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

	public boolean isDefalt(Species species) {
		
		HashMap<String, String> assign = gameFile.getFieldAll("assign_to_pop\\..*");
		
		if(assign != null) {
			return true;
		}
		return false;
	}

}
