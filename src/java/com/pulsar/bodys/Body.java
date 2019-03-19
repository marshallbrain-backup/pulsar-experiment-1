package bodys;

import java.util.ArrayList;
import java.util.Map;
import files.GameFile;
import math.RanAlg;
import math.Unit;
import universe.StarSystem;

public class Body {
	
	private Unit temperature;
	
	private StarSystem starSystem;

	public Body(Map<String, String> f, StarSystem s, double r) {
		
		Unit minT = new Unit(f.get("star_temp.min"));
		Unit maxT = new Unit(f.get("star_temp.max"));
		
		temperature = RanAlg.randomUnit(minT, maxT);
		
		starSystem = s;
		
	}

	public Body(GameFile f, StarSystem s, Body p, double r, ArrayList<Body> starList) {
		
		starSystem = s;
		
	}

}
