package ui.engine.vectors;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

import input.Keyboard;
import input.Mouse;
import ui.engine.Point;
import ui.engine.VectorGraphics;

@XmlRootElement(name = "vector_layer")
public class VectorGroup implements Cloneable {
	
	private int x;
	private int y;
	
	@XmlAnyElement(lax = true)
	private List<Vector> vectors;
	
	@XmlAnyAttribute
	private Map<QName, Object> parameters;
	private Map<String, Vector> mappedVectors;
	
	public List<Vector> getVectors(){
		return vectors;
	}
	
	public void init(Map<String, VectorGroup> vectorList) {
		
		for(int i = 0; i < vectors.size(); i++) {
			Vector v = vectors.get(i);
			switch(v.getType()) {
				case "link vector":
					LinkVector link = (LinkVector) v;
					link.assingParamerters(parameters);
					link.setLink(vectorList);
					break;
			}
		}
		
		init();
		
	}
	
	public void init() {
		
		mappedVectors = new HashMap<String, Vector>();
		for(Vector v: vectors) {
			mappedVectors.put(v.getId(), v);
		}
		
		for(Vector a: vectors) {
			a.assingParamerters(parameters);
		}
		
	}
	
	public String getAction(Mouse m, Keyboard k, Map<String, Area> a) {
		
		Point p = m.getPosition();
		if(a != null) {
			for(Entry<String, Area> e: a.entrySet()) {
				if(e.getValue().contains(p.getX(), p.getY())) {
					if(m.buttonClicked(1)) {
						String s = mappedVectors.get(e.getKey()).getAction("right click");
						System.out.println(s);
					}
				}
			}
		}
		
		return null;
	}
	
	public void draw(VectorGraphics vg) {
		vg.saveTransform();
		vg.translationMove(new Point(x, y));
		for(Vector v: vectors) {
			v.draw(vg);
		}
		vg.revertTransform();
	}
	
	public void setPosition(Point p) {
		x = p.getXInt();
		y = p.getYInt();
	}
	
	public void assingParameters(Object... p) {
		for(Entry<QName, Object> e: parameters.entrySet()) {
			parameters.put(e.getKey(), p[Integer.parseInt(e.getValue().toString())]);
		}
	}

	public void inherit(VectorGroup p) {
		for(Entry<String, Vector> e: mappedVectors.entrySet()) {
			e.getValue().inherit(p.mappedVectors.get(e.getKey()));
		}
	}
	
	@Override
	public VectorGroup clone() {
		
		try {
			
			VectorGroup clone = (VectorGroup) super.clone();
			clone.vectors = new ArrayList<Vector>();
			
			for(Vector v: vectors) {
				clone.vectors.add(v.clone());
			}
			
			clone.parameters = new HashMap<QName, Object>(parameters);
			clone.init();
			
			return clone;
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

}
