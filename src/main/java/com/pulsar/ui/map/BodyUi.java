package ui.map;

import java.awt.geom.Area;
import java.util.List;
import java.util.Map;

import bodys.Body;
import input.Keyboard;
import input.Mouse;
import pulsar.Main;
import ui.engine.EntrySet;
import ui.engine.Point;
import ui.engine.VectorGraphics;
import ui.engine.scripts.ScriptGroup;
import ui.engine.vectors.Vector;
import ui.engine.vectors.VectorGroup;

public class BodyUi {
	
	private Body body;
	private Map<String, Area> visibleArea;
	private VectorGroup vectors;
	private ScriptGroup scrip;
	
	private Map<String, VectorGroup> modifierVectors;
	
	public BodyUi(Body b, Map<String, VectorGroup> v, Map<String, VectorGroup> m, ScriptGroup s) {
		
		vectors = getVectors(b.getPath(), v);
		
		modifierVectors = m;
		scrip = s;
		body = b;
	}
	
	public boolean action(Mouse m, Keyboard k) {
		vectors.getAction(m, k, visibleArea);
		return false;
	}
	
	public void render(VectorGraphics vg, Long zoom) {
		
		if(body.getParent() != null) {
			for(Vector v: modifierVectors.get("orbit").getVectors()) {
				Vector vt = (Vector) v.clone();
				vt.move(new Point(body.getParent().getX(), body.getParent().getY()));
				vt.transform(new Point(body.getDistance(), 0));
				vt.normalize(zoom, Main.WIDTH, 8);
				vt.draw(vg);
			}
		}
		
		vg.startLogArea();
		
		for(Vector v: vectors.getVectors()) {
			Vector vt = (Vector) v.clone();
			vt.move(new Point(body.getX(), body.getY()));
			vt.transform(new Point(body.getRadius(), 0));
			vt.normalize(zoom, Main.WIDTH, 8);
			vt.draw(vg);
		}
		
		if(body.getColony() != null) {
			for(Vector v: modifierVectors.get("colony").getVectors()) {
				Vector vt = (Vector) v.clone();
				vt.move(new Point(body.getX(), body.getY()));
				vt.transform(new Point(body.getRadius(), 0));
				vt.normalize(zoom, Main.WIDTH, 8);
				vt.draw(vg);
			}
		}
		
		Map<String, Area> a = vg.stopLogArea();
		if(a != null) {
			visibleArea = a;
		}
		
	}
	
	private VectorGroup getVectors(String id, Map<String, VectorGroup> v){
		try {
			return v.getOrDefault(id, getVectors(id.substring(0, id.lastIndexOf(".")), v));
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
	
}
