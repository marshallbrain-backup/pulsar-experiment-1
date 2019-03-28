package bodys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import files.GameFile;
import math.RanAlg;
import universe.StarSystem;

public class Body {
	
	private long temperature;
	private long radius;
	
	private StarSystem starSystem;

	public Body(Map<String, String> f, StarSystem s, double r) {
		
		double sr = convert("6.95700e8");
		
		double minT = convert(f.get("set_temp.min"));
		double maxT = convert(f.get("set_temp.max"));
		double minS = convert(f.get("size.min"))*sr;
		double maxS = convert(f.get("size.max"))*sr;
		
		double tem = RanAlg.randomDouble(minT, maxT, 0);
		double rad = RanAlg.randomDouble(minS, maxS, 0);
		
		temperature = Math.round(tem);
		radius = Math.round(rad);
		
		starSystem = s;
		
	}

	public Body(GameFile f, Map<String, String> temps, Map<String, String> prob, StarSystem s, Body parent, double rad, Body star) {
			
		double t = star.temperature;
		double r = star.radius;
		
		double tem = t * Math.sqrt(r/(2*rad));
		
		temperature = Math.round(tem);
		
		String[] k = new String[temps.size()];
		k = temps.keySet().toArray(k);
		List<String> planetPosibilitys = new ArrayList<String>();
		
		for(int i = 0; i < k.length; i+=2) {
			
			long min = Long.parseLong(temps.get(k[i]));
			long max = Long.parseLong(temps.get(k[i+1]));
			
			if(min < temperature && temperature < max) {
				planetPosibilitys.add(k[i].split("\\.")[0]);
			}
			
		}
		
		List<Double> probList = new ArrayList<Double>();
		double m = 0;
		
		for(Entry<String, String> p : prob.entrySet()) {
			
			if(planetPosibilitys.contains(p.getKey().split("\\.")[0])) {
				probList.add(Double.parseDouble(p.getValue()));
				m += Double.parseDouble(p.getValue());
			}
			
		}
		
		radius = Math.round(rad);
		
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
