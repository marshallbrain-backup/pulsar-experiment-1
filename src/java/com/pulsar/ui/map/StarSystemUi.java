package ui.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import bodys.Body;
import pulsar.Main;
import ui.engine.ScreenPosition;
import ui.engine.Vector;
import ui.engine.VectorGraphics;
import universe.StarSystem;

public class StarSystemUi implements Chart {
	
	private List<Vector> bodys;
	
	private StarSystem starSystem;
	
	public StarSystemUi(Map<String, List<Vector>> vectorList, StarSystem ss) {
		
		starSystem = ss;
		Body s = starSystem.getBodys();
		bodys = new ArrayList<Vector>();
		
		for(Entry<String, List<Vector>> e: vectorList.entrySet()) {
			if(e.getKey().equals(s.getType())) {
				for(Vector v: e.getValue()) {
					v.setSize(s.getRadius());
					bodys.add(v);
				}
			}
		}
		
	}

	@Override
	public void render(VectorGraphics g) {
		
		g.translationSet(ScreenPosition.CENTER);
		bodys.get(0).setTempSize(Math.round(696000000.0*10), Main.WIDTH, 5);
		g.draw(bodys.get(0));
		
	}

}