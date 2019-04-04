package ui.map;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bodys.Body;
import input.Keyboard;
import input.Mouse;
import pulsar.Main;
import ui.engine.UiElement;
import ui.engine.Circle;
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
	private Point offsetZoom;
	private StarSystem starSystem;
	
	public StarSystemUi(Map<String, List<Vector>> vectorList, StarSystem ss) {
		
		starSystem = ss;
		
		//TODO set zoom equal to system max radius/1 au
		zoom = 1;
		
		bodyVectors = new HashMap<String, List<Vector>>();
		bodys = new ArrayList<Body>();
		offsetAmount = new Point(0, 0);
		offsetZoom = new Point(0, 0);
		
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
			int z = zoom+m.getWheelDir();
			if(z <= 0) {
				z = 1;
			}
			
			Point n = new Point(m.getPosition().getX()-Main.WIDTH/2, m.getPosition().getY()-Main.HEIGHT/2, Main.WIDTH, getZoom(z));
			Point o = new Point(m.getPosition().getX()-Main.WIDTH/2, m.getPosition().getY()-Main.HEIGHT/2, Main.WIDTH, getZoom(zoom));
			
			offsetZoom.move(new Point(n, o));
			
			zoom = z;
			
		}
		
		if(m.buttonDown(1)) {
			Point d = m.getChange();
			if(d.getX() != 0 || d.getY() != 0) {
				offsetAmount.move(-d.getX(), -d.getY(), Main.WIDTH, getZoom(zoom));
				return true;
			}
		}
		
		return true;
		
	}

	@Override
	public void render(VectorGraphics g) {
		
		g.translationSet(ScreenPosition.CENTER);
		g.translationMove(new Point(offsetAmount, getZoom(zoom), Main.WIDTH));
		g.translationMove(new Point(offsetZoom, getZoom(zoom), Main.WIDTH));
		
		for(int i = 0; i < bodys.size(); i++) {
			
			Body b = bodys.get(i);
			
			Vector orbit = new Circle(Color.BLACK, Math.round(b.getParent().getX()), Math.round(b.getParent().getY()), b.getParent().getDistance());
			g.draw(orbit);
			
			for(Vector v: bodyVectors.get(b.getType())) {
				Vector vt = v.copy();
				vt.move(new Point(b.getX(), b.getY()));
				vt.transform(b.getRadius());
				vt.normalize(getZoom(zoom), Main.WIDTH, 8);
				g.draw(vt);
			}
			
		}
		
	}
	
	private long getZoom(int z) {
		if(zoom > 0) {
			return Math.round(149597870700.0/Math.abs(z));
		}
		if(zoom < 0) {
			return Math.round(149597870700.0*Math.abs(z));
		}
		
		return Math.round(149597870700.0);
		
	}

}
