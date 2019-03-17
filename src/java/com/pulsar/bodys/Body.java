package bodys;

import java.util.Map;
import files.GameFile;
import universe.StarSystem;

public class Body {
	
	protected StarSystem starSystem;

	public Body(GameFile gf, StarSystem s, double r) {
		
		starSystem = s;
		
	}

	public Body(Map<String, String> star, StarSystem s, double r) {
		
		starSystem = s;
		
	}

}
