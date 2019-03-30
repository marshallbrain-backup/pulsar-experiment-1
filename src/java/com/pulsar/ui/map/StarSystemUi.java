package ui.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bodys.Body;
import pulsar.Main;
import ui.engine.ScreenPosition;
import ui.engine.Vector;
import ui.engine.VectorGraphics;
import universe.StarSystem;

public class StarSystemUi implements Chart {
	
	private List<Vector> bodyVectors;
	private List<Body> bodys;
	
	private StarSystem starSystem;
	
	public StarSystemUi(Map<String, List<Vector>> vectorList, StarSystem ss) {
		
		starSystem = ss;
		bodyVectors = new ArrayList<Vector>();
		bodys = new ArrayList<Body>();
		
		for(Body b: starSystem.getBodys()) {
			List<Vector> e = vectorList.get(b.getType());
			if(e != null) {
				for(Vector v: e) {
					bodys.add(b);
					bodyVectors.add(v);
				}
			} else {
				bodys.add(b);
				bodyVectors.add(vectorList.get("_default").get(0));
			}
		}
		
	}

	@Override
	public void render(VectorGraphics g) {
		
		g.translationSet(ScreenPosition.CENTER);
		for(int i = 0; i < bodys.size(); i++) {
			Body b = bodys.get(i);
			Vector v = bodyVectors.get(i);
			Vector vt = v.transform(b.getDistance(), b.getAngle(), b.getRadius(), Math.round(149597870700.0*5), Main.WIDTH, 5);
			g.draw(vt);
		}
		
	}

}
