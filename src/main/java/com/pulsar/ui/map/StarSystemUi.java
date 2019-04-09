package ui.map;

import java.awt.Color;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bodys.Body;
import input.Keyboard;
import input.Mouse;
import pulsar.Main;
import ui.engine.UiElement;
import ui.engine.Circle;
import ui.engine.EntrySet;
import ui.engine.Point;
import ui.engine.ScreenPosition;
import ui.engine.Vector;
import ui.engine.VectorGraphics;
import ui.view.View;
import ui.view.ViewColony;
import universe.StarSystem;

public class StarSystemUi implements UiElement {
	
	private int zoom;
	
	private List<Body> bodys;
	private List<View> views;
	private List<EntrySet<Area, Body>> Areas;
	
	private Map<String, List<Vector>> bodyVectors;
	private Map<String, List<Vector>> modifierVectors;
	
	private Point offsetAmount;
	private Point offsetZoom;
	private StarSystem starSystem;
	
	public StarSystemUi(Map<String, List<Vector>> vectorList, StarSystem ss, List<View> vi) {
		
		starSystem = ss;
		views = vi;
		
		zoom = 1;
		
		bodyVectors = new HashMap<String, List<Vector>>();
		modifierVectors = new HashMap<String, List<Vector>>();
		bodys = new ArrayList<Body>();
		Areas = new ArrayList<EntrySet<Area, Body>>();
		offsetAmount = new Point(0, 0);
		offsetZoom = new Point(0, 0);
		
		for(Body b: starSystem.getBodys()) {
			List<Vector> v = vectorList.get(b.getTypePath());
			if(v != null) {
				bodys.add(b);
				bodyVectors.putIfAbsent(b.getType(), v);
				modifierVectors.putIfAbsent("colony", vectorList.get("modifiers.colony"));
			} else {
				bodys.add(b);
				bodyVectors.putIfAbsent(b.getTypePath(), vectorList.get("body._default"));
			}
		}
		
	}

	@Override
	public boolean action(Mouse m, Keyboard k) {
		
		Point mp = new Point(m.getPosition());
		
		if(m.buttonDownOnce(1)) {
				
			ArrayList<EntrySet<Area, Body>> cl = new ArrayList<EntrySet<Area, Body>>(Areas);
			
			for(EntrySet<Area, Body> e: cl) {
				if(e.getKey().contains(mp.getX(), mp.getY())) {
					views.add(new ViewColony());
				}
			}
			
		} else if(m.buttonDown(1)) {
			Point d = m.getChange();
			if(d.getX() != 0 || d.getY() != 0) {
				offsetAmount.move(-d.getX(), -d.getY(), Main.WIDTH, getZoom(zoom));
				return true;
			}
		} else if(m.getWheelDir() != 0) {
			int z = zoom+m.getWheelDir();
			if(z == 0) {
				z += m.getWheelDir();
			}
			
			Point n = new Point(m.getPosition().getX()-Main.WIDTH/2, m.getPosition().getY()-Main.HEIGHT/2, Main.WIDTH, getZoom(z));
			Point o = new Point(m.getPosition().getX()-Main.WIDTH/2, m.getPosition().getY()-Main.HEIGHT/2, Main.WIDTH, getZoom(zoom));
			
			offsetZoom.move(new Point(n, o));
			
			zoom = z;
			
		}
		
		return true;
		
	}

	@Override
	public void render(VectorGraphics g) {
		
		Areas.clear();
		
		g.translationSet(ScreenPosition.CENTER);
		g.translationMove(new Point(offsetAmount, getZoom(zoom), Main.WIDTH));
		g.translationMove(new Point(offsetZoom, getZoom(zoom), Main.WIDTH));
		
		for(int i = 0; i < bodys.size(); i++) {
			
			Body b = bodys.get(i);
			
			if(b.getParent() != null) {
				Vector orbit = new Circle(Color.BLACK, Math.round(b.getParent().getX()), Math.round(b.getParent().getY()), b.getDistance());
				orbit.normalize(getZoom(zoom), Main.WIDTH, 0);
				g.draw(orbit);
			}
			
			g.startLogArea();
			
			for(Vector v: bodyVectors.get(b.getType())) {
				Vector vt = v.copy();
				vt.move(new Point(b.getX(), b.getY()));
				vt.transform(b.getRadius());
				vt.normalize(getZoom(zoom), Main.WIDTH, 8);
				g.fill(vt);
			}
			
			if(b.getColony() != null) {
				for(Vector v: modifierVectors.get("colony")) {
					Vector vt = v.copy();
					vt.move(new Point(b.getX(), b.getY()));
					vt.transform(b.getRadius());
					vt.normalize(getZoom(zoom), Main.WIDTH, 8);
					g.fill(vt);
				}
			}
			
			Area a = g.stopLogArea();
			if(a != null) {
				Areas.add(new EntrySet<>(a, b));
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
