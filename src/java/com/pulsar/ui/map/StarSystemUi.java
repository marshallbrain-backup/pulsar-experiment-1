package ui.map;

import java.util.ArrayList;
import java.util.HashMap;
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
	
	private int zoom;
	
	private List<Body> bodys;
	
	private Map<String, List<Vector>> bodyVectors;
	
	private Point offsetAmount;
	private StarSystem starSystem;
	
	public StarSystemUi(Map<String, List<Vector>> vectorList, StarSystem ss) {
		
		starSystem = ss;
		
		zoom = 1;
		
		bodyVectors = new HashMap<String, List<Vector>>();
		bodys = new ArrayList<Body>();
		offsetAmount = new Point(0, 0);
		
		for(Body b: starSystem.getBodys()) {
			List<Vector> v = vectorList.get(b.getType());
			if(v != null) {
				bodys.add(b);
				bodyVectors.putIfAbsent(b.getType(), v);
			} else {
				bodys.add(b);
				bodyVectors.putIfAbsent(b.getType(), vectorList.get("_default"));
			}
		}
		
	}

	@Override
	public boolean action(Mouse m, Keyboard k) {
		
		if(m.getWheelDir() != 0) {
			zoom += m.getWheelDir();
			if(zoom == 0) {
				zoom += m.getWheelDir();
			}
		}
		
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
			List<Vector> vectList = bodyVectors.get(b.getType());
			for(Vector v: vectList) {
				Vector vt = v.copy();
				vt.transform(b.getDistance(), b.getAngle(), b.getRadius(), getZoom(), Main.WIDTH, 8);
				g.draw(vt);
			}
		}
		
	}
	
	private long getZoom() {
		if(zoom > 0) {
			return Math.round(149597870700.0/Math.abs(zoom));
		}
		if(zoom < 0) {
			return Math.round(149597870700.0*Math.abs(zoom));
		}
		
		return Math.round(149597870700.0);
		
	}

}
