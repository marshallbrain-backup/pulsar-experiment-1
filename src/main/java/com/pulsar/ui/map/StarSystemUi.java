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
import ui.engine.EntrySet;
import ui.engine.Point;
import ui.engine.ScreenPosition;
import ui.engine.VectorGraphics;
import ui.engine.scripts.ScriptGroup;
import ui.engine.vectors.Vector;
import ui.engine.vectors.VectorGroup;
import universe.StarSystem;

public class StarSystemUi implements UiElement {
	
	private int zoom;
	
	private List<Body> bodys;
	private List<BodyUi> bodyUis;
	private List<EntrySet<Area, Body>> Areas;
	
	private Point offsetAmount;
	private Point offsetZoom;
	private StarSystem starSystem;
	
	public StarSystemUi(Map<String, VectorGroup> vectorList, Map<String, ScriptGroup> scriptsList, StarSystem ss) {
		
		starSystem = ss;
		
		zoom = 1;
		
		bodys = new ArrayList<Body>();
		bodyUis = new ArrayList<BodyUi>();
		Areas = new ArrayList<EntrySet<Area, Body>>();
		offsetAmount = new Point(0, 0);
		offsetZoom = new Point(0, 0);
		
		Map<String, VectorGroup> mv = new HashMap<String, VectorGroup>();
		mv.put("orbit", vectorList.get("orbit"));
		mv.put("colony", vectorList.get("modifiers.colony"));
		
		for(Body b: starSystem.getBodys()) {
			bodyUis.add(new BodyUi(b, vectorList, scriptsList, mv));
		}
		
	}

	@Override
	public boolean action(Mouse m, Keyboard k) {
		
		for(BodyUi b: bodyUis) {
			b.action(m, k);
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
		
		for(BodyUi b: bodyUis) {
			b.render(vg, getZoom(zoom));
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
