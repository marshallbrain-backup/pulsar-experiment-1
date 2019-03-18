package universe;

import java.util.ArrayList;
import java.util.Map;
import algorithms.RanAlg;
import bodys.Body;
import files.GameFile;
import species.Species;

public class StarSystem {
	
	private double systemRadius;
	
	private ArrayList<Body> starList;
	private ArrayList<Body> planetList;
	
	public StarSystem(GameFile gf, Map<String, String> system, Species s) {
		
		starList = new ArrayList<Body>();
		planetList = new ArrayList<Body>();
		
		init(gf, system);
		
	}
	
	private void init(GameFile gf, Map<String, String> system) {
		
		Map<String, String> star = gf.getFieldAll("body_classes\\." + system.get("star") + "\\..*", 0);
		
		starList.add(new Body(star, this, 0.0));
		
		generatePlanets(gf, system.get("num_planets.min"), system.get("num_planets.max"));
		
	}
	
	private void generatePlanets(GameFile gf, String min, String max) {
		
		int n = RanAlg.randomInt(Integer.parseInt(min), Integer.parseInt(max));
		double r = RanAlg.randomDouble(0.2, 0.7, 2);
		
		for(int i = 0; i < n; i++) {
			//TODO this
			planetList.add(new Body(gf, this, r));
			if(systemRadius < r)
				systemRadius = r;
			r = RanAlg.randomDouble(r, 0.5, 1.5, 2);
		}
		
	}

}
