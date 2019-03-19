package bodys;

import java.util.ArrayList;
import java.util.Map;
import files.GameFile;
import universe.StarSystem;

public class Body {
	
	protected StarSystem starSystem;

	public Body(Map<String, String> f, StarSystem s, double r) {
		
		starSystem = s;
		
	}

	public Body(GameFile f, StarSystem s, Body p, double r, ArrayList<Body> starList) {
		
		starSystem = s;
		
	}

}
