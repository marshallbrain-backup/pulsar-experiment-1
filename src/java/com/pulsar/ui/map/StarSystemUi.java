package ui.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bodys.Body;
import input.Keyboard;
import input.Mouse;
import pulsar.Main;
import ui.engine.UiElement;
import ui.engine.Point;
import ui.engine.ScreenPosition;
import ui.engine.Vector;
import ui.engine.VectorGraphics;
import universe.StarSystem;

public class StarSystemUi implements UiElement {
	
	private List<Vector> bodyVectors;
	private List<Body> bodys;
	
	private Point offsetAmount;
	private StarSystem starSystem;
	
	public StarSystemUi(Map<String, List<Vector>> vectorList, StarSystem ss) {
		
		starSystem = ss;
		
		bodyVectors = new ArrayList<Vector>();
		bodys = new ArrayList<Body>();
		offsetAmount = new Point(0, 0);
		
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
	public boolean action(Mouse m, Keyboard k) {
		
		if(m.buttonDown(1)) {
			Point d = m.getChange();
			if(d.getX() != 0 || d.getY() != 0) {
				offsetAmount.move(-d.getX(), -d.getY());
				return true;
			}
		}
		
		return true;
		
	}

	@Override
	public void render(VectorGraphics g) {
		
		g.translationSet(ScreenPosition.CENTER);
		g.translationMove(offsetAmount);
		for(int i = 0; i < bodys.size(); i++) {
			Body b = bodys.get(i);
			Vector v = bodyVectors.get(i);
			Vector vt = v.transform(b.getDistance(), b.getAngle(), b.getRadius(), Math.round(149597870700.0*5), Main.WIDTH, 8);
			g.draw(vt);
		}
		
	}

}
