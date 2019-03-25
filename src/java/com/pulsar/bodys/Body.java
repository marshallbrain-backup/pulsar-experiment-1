package bodys;

import java.util.ArrayList;
import java.util.Map;

import files.GameFile;
import math.RanAlg;
import universe.StarSystem;

public class Body {
	
	private double temperature;
	private double radius;
	
	private StarSystem starSystem;

	public Body(Map<String, String> f, StarSystem s, double r) {
		
		double sr = convert("6.95700e8");
		
		double minT = convert(f.get("temp.min"));
		double maxT = convert(f.get("temp.max"));
		double minS = convert(f.get("size.min"))*sr;
		double maxS = convert(f.get("size.max"))*sr;
		
		temperature = RanAlg.randomDouble(minT, maxT, 0);
		radius = RanAlg.randomDouble(minS, maxS, 0);
		
		starSystem = s;
		
	}

	public Body(GameFile f, StarSystem s, Body p, double rd, Body star) {
			
		double t = star.temperature;
		double r = star.radius;
		
		double temperature = t * Math.sqrt(r/(2*rd));
		
		starSystem = s;
		
	}
	
	private double convert(String n) {
		
		String[] s = n.split("e");
		
		if(s.length == 1)
			return Double.parseDouble(n);
		
		long e = Long.parseLong(s[1]);
		String i = s[0];
		
		double d = Double.parseDouble(i);
		
		return d * Math.pow(10, e);
		
	}

}
