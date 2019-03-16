package files.type;

import java.util.Random;

import algorithms.Match;
import files.GameFile;

//TODO change to an interface
//TODO add specific error with checkFile
public class Type {
	
	protected String name;
	protected Random r;
	
	protected GameFile gameFile;

	public Type(GameFile f) {
		gameFile = f;
		name = gameFile.getName();
		
		r = new Random();
	}
	
	public String getField(String name) {
		return gameFile.getField(name);
	}

	protected boolean isString(String var) {
		
		if(var == null || var.isEmpty()) return false;
		
		return true;
	}

	protected boolean isInt(String var) {
		if(var == null || (var.isEmpty() && !Match.isInt(var))) return false;
		return true;
	}

	protected boolean isDouble(String var) {
		if(var == null || (var.isEmpty() && !Match.isDouble(var))) return false;
		return true;
	}

	protected boolean isBoolean(String var) {
		if(var == null || (var.isEmpty() && !Boolean.valueOf(var))) return false;
		return true;
	}

	public String getName() {
		return name;
	}

}
