package universe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bodys.Body;
import files.GameFile;
import math.RanAlg;
import species.Species;

public class StarSystem {
	
	private double systemRadius;
	
	private Body star;
	
	private List<Body> planetList;
	
	public StarSystem(GameFile gf, Map<String, String> system, Species s) {
		
		planetList = new ArrayList<Body>();
		
		init(gf, system);
		
	}
	
	private void init(GameFile gf, Map<String, String> system) {
		
		Map<String, String> s = gf.getFieldAll(system.get("star") + "\\..*", 1);
		
		star = new Body(system.get("star"), s, this, 0.0);
		
		generatePlanets(gf, star, system.get("num_planets.min"), system.get("num_planets.max"));
		
	}
	
	private void generatePlanets(GameFile gf, Body star, String min, String max) {
		
		int n = RanAlg.randomInt(Integer.parseInt(min), Integer.parseInt(max));
		double radius = RanAlg.randomDouble(0.2, 0.7, 2);
		double[] radiusList = new double[n];
		
		for(int i = 0; i < n; i++) {
			radiusList[i] = radius;
			if(systemRadius < radius)
				systemRadius = radius;
			radius = RanAlg.randomDouble(radius, 0.5, 1.5, 2);
		}
		
		Map<String, String> temps = gf.getFieldAll(".*\\.range_temp\\..*", 0);
		Map<String, String> prob = gf.getFieldAll(".*\\.spawn_odds", 0);
		
		for(double r: radiusList) {
			planetList.add(new Body(gf, temps, prob, this, star, Math.round(r*149597870700.0), star));
		}
		
	}

	public List<Body> getBodys() {
		List<Body> bodys = new ArrayList<Body>();
		bodys.add(star);
		bodys.addAll(planetList);
		return bodys;
	}

}
