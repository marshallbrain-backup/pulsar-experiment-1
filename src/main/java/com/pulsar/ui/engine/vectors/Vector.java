package ui.engine.vectors;

import java.awt.Shape;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import ui.engine.Point;
import ui.engine.VectorGraphics;

public interface Vector extends Cloneable {

	String getType();
	String getId();
	String getAction(String id, String action);
	
	Vector clone();

	Shape getShape();
	
	Map<String, String> getStyle();
	
	void inherit(Vector v);
	void draw(VectorGraphics vg);
	void move(Point offset);
	void transform(Point offset);
	void normalize();
	void normalize(long screenSize, int screenWidth, int minSize);
	void setStyle();
	void assingParamerters(Map<QName, Object> p);
	
	default void init() {
	}
	
	default long convert(double value, double fromRefrence, double toRefrence) {
		return Math.round(((value/fromRefrence)*toRefrence));
	}
	
	default Map<String, String> convertStyle(String s) {
		
		if(s == null) {
			return null;
		}
		
		Map<String, String> style = new HashMap<String, String>();
		
		for(String e: s.split(";")) {
			
			String key = e.split(":")[0];
			String value = e.split(":")[1];
			
			style.put(key, value);
			
		}
		
		return style;
		
	}

}
