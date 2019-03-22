package bodys;

import java.util.ArrayList;
import java.util.Map;
import files.GameFile;
import math.RanAlg;
import math.Unit;
import math.UnitType;
import universe.StarSystem;

public class Body {
	
	private Unit temperature;
	private Unit radius;
	
	private StarSystem starSystem;

	public Body(Map<String, String> f, StarSystem s, double r) {
		
		Unit minT = new Unit(f.get("star_temp.min"));
		Unit maxT = new Unit(f.get("star_temp.max"));
		Unit minS = new Unit(f.get("star_size.min"));
		Unit maxS = new Unit(f.get("star_size.max"));
		
		temperature = RanAlg.randomUnit(minT, maxT);
		radius = RanAlg.randomUnit(minS, maxS);
		
		starSystem = s;
		
	}

	public Body(GameFile f, StarSystem s, Body p, double rd, ArrayList<Body> starList) {
		
		for(Body star: starList) {
			
			double t = star.temperature.doubleValue();
			double r = star.radius.doubleValue();
			
			double bt = t * Math.sqrt(r/(2*rd));
			
			temperature = new Unit(Math.round(bt));
			
		}
		
		starSystem = s;
		
	}

}
