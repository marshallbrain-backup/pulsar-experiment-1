package ui.engine.vectors;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

import input.Keyboard;
import input.Mouse;
import ui.engine.Point;
import ui.engine.VectorGraphics;

@XmlRootElement(name = "vector_layer")
public class VectorGroup implements Cloneable {
	
	@XmlAttribute(name = "x")
	private int x;
	@XmlAttribute(name = "y")
	private int y;
	
	@XmlAnyElement(lax = true)
	private List<Vector> vectors;
	
	private Map<QName, Object> parameters;
	private Map<String, Vector> mappedVectors;
	
	public List<Vector> getVectors(){
		return vectors;
	}
	
	public void init() {
		
		mappedVectors = new HashMap<String, Vector>();
		
		for(Vector v: vectors) {
			mappedVectors.put(v.getId(), v);
			v.assingParamerters(parameters);
			v.setStyle();
			v.init();
		}
		
	}
	
	public String getAction(Mouse m, Keyboard k, Map<String, Area> a) {
		
		Point p = m.getPosition();
		if(a != null) {
			for(Entry<String, Area> e: a.entrySet()) {
				
				if(mappedVectors.containsKey(e.getKey().split(" ")[0])) {
					
					String s = mappedVectors.get(e.getKey().split(" ")[0]).getAction(e.getKey(), "generic");
					if(s != null) {
						return s;
					}
					
					if(e.getValue().contains(p.getX(), p.getY())) {
						
						if(m.buttonClicked(1)) {
							s = mappedVectors.get(e.getKey().split(" ")[0]).getAction(e.getKey(), "right click");
							return s;
						}
						
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
	
	public Vector getElementById(String i) {
		return mappedVectors.get(i);
	}
	
	@Override
	public VectorGroup clone() {
		
		try {
			
			VectorGroup clone = (VectorGroup) super.clone();
			clone.vectors = new ArrayList<Vector>();
			
			for(Vector v: vectors) {
				clone.vectors.add(v.clone());
			}
			
//			clone.parameters = new HashMap<QName, Object>(parameters);
			clone.init();
			
			return clone;
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

}
