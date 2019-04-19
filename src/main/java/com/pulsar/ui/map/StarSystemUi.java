package ui.map;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bodys.Body;
import input.Keyboard;
import input.Mouse;
import pulsar.Main;
import ui.engine.UiElement;
import ui.ActionHandler;
import ui.engine.EntrySet;
import ui.engine.Point;
import ui.engine.ScreenPosition;
import ui.engine.VectorGraphics;
import ui.engine.actions.ActionGroup;
import ui.engine.vectors.Vector;
import ui.engine.vectors.VectorGroup;
import universe.StarSystem;

public class StarSystemUi implements UiElement {
	
	private int zoom;
	
	private List<Body> bodys;
	private List<EntrySet<Area, Body>> Areas;
	
	private Map<String, List<Vector>> bodyVectors;
	private Map<String, List<Vector>> modifierVectors;
	
	private Point offsetAmount;
	private Point offsetZoom;
	private StarSystem starSystem;
	private ActionHandler actionHandler;
	
	public StarSystemUi(Map<String, VectorGroup> vectorList, Map<String, ActionGroup> actionList, ActionHandler ah, StarSystem ss) {
		
		actionHandler = ah;
		starSystem = ss;
		
		zoom = 1;
		
		bodyVectors = new HashMap<String, List<Vector>>();
		modifierVectors = new HashMap<String, List<Vector>>();
		bodys = new ArrayList<Body>();
		Areas = new ArrayList<EntrySet<Area, Body>>();
		offsetAmount = new Point(0, 0);
		offsetZoom = new Point(0, 0);
		
		bodyVectors.put("orbit", vectorList.get("orbit").getVectors());
		modifierVectors.putIfAbsent("colony", vectorList.get("modifiers.colony").getVectors());
		
		for(Body b: starSystem.getBodys()) {
			List<Vector> v = vectorList.get(b.getPath()).getVectors();
			if(v != null) {
				bodys.add(b);
				bodyVectors.putIfAbsent(b.getType(), v);
			} else {
				bodys.add(b);
				bodyVectors.putIfAbsent(b.getPath(), vectorList.get("body").getVectors());
			}
		}
		
	}

	@Override
	public boolean action(Mouse m, Keyboard k) {
		
		ArrayList<EntrySet<Area, Body>> cl = new ArrayList<EntrySet<Area, Body>>(Areas);
		
		for(EntrySet<Area, Body> e: cl) {
			if(actionHandler.performAction(m, k, e.getValue().getFullPath(), e.getKey(), e.getValue())) {
				return true;
			}
		}
		
		if(m.buttonDown(1)) {
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
		
		return false;
		
	}

	@Override
	public void render(VectorGraphics vg) {
		
		Areas.clear();
		
		vg.translationSet(ScreenPosition.CENTER);
		vg.translationMove(new Point(offsetAmount, getZoom(zoom), Main.WIDTH));
		vg.translationMove(new Point(offsetZoom, getZoom(zoom), Main.WIDTH));
		
		for(int i = 0; i < bodys.size(); i++) {
			
			Body b = bodys.get(i);
			
			if(b.getParent() != null) {
				for(Vector v: bodyVectors.get("orbit")) {
					Vector vt = (Vector) v.clone();
					vt.move(new Point(b.getParent().getX(), b.getParent().getY()));
					vt.transform(new Point(b.getDistance(), 0));
					vt.normalize(getZoom(zoom), Main.WIDTH, 8);
					vt.draw(vg);
				}
			}
			
			vg.startLogArea();
			
			for(Vector v: bodyVectors.get(b.getType())) {
				Vector vt = (Vector) v.clone();
				vt.move(new Point(b.getX(), b.getY()));
				vt.transform(new Point(b.getRadius(), 0));
				vt.normalize(getZoom(zoom), Main.WIDTH, 8);
				vt.draw(vg);
			}
			
			if(b.getColony() != null) {
				for(Vector v: modifierVectors.get("colony")) {
					Vector vt = (Vector) v.clone();
					vt.move(new Point(b.getX(), b.getY()));
					vt.transform(new Point(b.getRadius(), 0));
					vt.normalize(getZoom(zoom), Main.WIDTH, 8);
					vt.draw(vg);
				}
			}
			
			Area a = vg.stopLogArea();
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
