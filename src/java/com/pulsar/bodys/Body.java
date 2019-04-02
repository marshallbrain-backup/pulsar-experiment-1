package bodys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import files.GameFile;
import math.RanAlg;
import universe.StarSystem;

public class Body {
	
	private long temperature;
	private long radius;
	private long distance;
	
	private double angle;
	
	private String bodyType;
	
	private StarSystem starSystem;

	public Body(String t, Map<String, String> f, StarSystem s, double r) {
		
		bodyType = t;
		starSystem = s;
		
		double sr = convert("6.95700e8");
		
		double minT = convert(f.get("set_temp.min"));
		double maxT = convert(f.get("set_temp.max"));
		double minS = convert(f.get("size.min"))*sr;
		double maxS = convert(f.get("size.max"))*sr;
		
		double tem = RanAlg.randomDouble(minT, maxT, 0);
		double rad = RanAlg.randomDouble(minS, maxS, 0);
		
		temperature = Math.round(tem);
		radius = Math.round(rad);
		distance = 0;
		angle = 0;
		
	}

	public Body(GameFile f, Map<String, String> temps, Map<String, String> prob, StarSystem s, Body parent, long dist, Body star) {
			
		double t = star.temperature;
		double r = star.radius;
		
		double tem = t * Math.sqrt(r/(2*dist));
		
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
		
		Map<String ,Double> probList = new HashMap<String ,Double>();
		double m = 0;
		
		for(Entry<String, String> p : prob.entrySet()) {
			
			String plan = p.getKey().split("\\.")[0];
			
			if(planetPosibilitys.contains(plan)) {
				probList.put(plan, Double.parseDouble(p.getValue()));
				m += Double.parseDouble(p.getValue());
			}
			
		}
		
		double rand = RanAlg.randomDouble(0, m, 5);
		String planet = "";
		
		for(Entry<String, Double> d: probList.entrySet()) {
			rand -= d.getValue();
			if(rand <= 0) {
				planet = d.getKey();
			}
		}
		
		Map<String, String> file = f.getFieldAll(planet, 1);
		
		double minS = convert(file.get("size.min"));
		double maxS = convert(file.get("size.max"));
		
		double rad = RanAlg.randomDouble(minS, maxS, 0);
		
		temperature = Math.round(tem);
		radius = Math.round(rad);
		
		bodyType = planet;
		radius = Math.round(rad);
		distance = dist;
		angle = 0;
		
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

	public String getType() {
		return bodyType;
	}
	
	public long getRadius() {
		return radius;
	}
	
	public long getDistance() {
		return distance;
	}
	
	public double getAngle() {
		return angle;
	}
	
}
