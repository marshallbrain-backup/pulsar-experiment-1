package ui.engine.vectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

import ui.engine.VectorGraphics;

@XmlRootElement(name = "vector_layer")
public class VectorGroup implements Cloneable {

	@XmlAnyElement(lax = true)
	private List<Vector> vectors;
	
	@XmlAnyAttribute
	private Map<QName, Object> parameters;
	
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
		for(Vector a: vectors) {
			a.assingParamerters(parameters);
		}
	}
	
	public void draw(VectorGraphics vg) {
		for(Vector v: vectors) {
			v.draw(vg);
		}
	}
	
	public void assingParameters(Object... p) {
		for(Entry<QName, Object> e: parameters.entrySet()) {
			parameters.put(e.getKey(), p[Integer.parseInt(e.getValue().toString())]);
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
