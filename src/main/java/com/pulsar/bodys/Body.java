package bodys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import files.GameFile;
import math.RanAlg;
import species.colony.Colony;
import universe.StarSystem;

public class Body {
	
	private long temperature;
	private long radius;
	private long distance;
	
	private double angle;
	
	private boolean colonizable;
	
	private String bodyType;
	
	private Body parent;
	private StarSystem starSystem;
	private Colony colony;

	public Body(String t, Map<String, String> f, StarSystem s, double r) {
		
		parent = null;
		
		bodyType = t;
		starSystem = s;
		
		init(f);
		
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

	public Body(GameFile f, Map<String, String> temps, Map<String, String> prob, StarSystem s, Body par, long dist, Body star) {
		
		angle = RanAlg.randomDouble(0, 360);

		distance = dist;
		parent = par;
		starSystem = s;
			
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

		init(file);
		
	}
	
	private void init(Map<String, String> f) {
		
		if(f.containsKey("colonizable")) {
			if(f.get("colonizable").equals("yes")) {
				colonizable = true;
			}
		} else {
			colonizable = true;
		}
		
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

	public String getTypePath() {
		return "body." + bodyType;
	}
	
	public long getDistance() {
		return distance;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public double getX() {
		return Math.cos(Math.toRadians(angle))*distance;
	}
	
	public double getY() {
		return Math.sin(Math.toRadians(angle))*distance;
	}
	
	public long getRadius() {
		return radius;
	}
	
	public Body getParent() {
		return parent;
	}

	public void setColony(Colony c) {
		colony = c;
	}

	public Colony getColony() {
		return colony;
	}

	public boolean isColonizable() {
		return colonizable;
	}
	
}
