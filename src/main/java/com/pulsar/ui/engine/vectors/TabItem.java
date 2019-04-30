package ui.engine.vectors;

import java.awt.Shape;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import ui.engine.VectorGraphics;

@XmlRootElement(name = "tab_item")
public class TabItem {
	
	@XmlAttribute(name = "style")
	private String styleString;
	@XmlAttribute(name = "text")
	private String text;
	
	private VectorGroup vector;
	
	private Map<String, String> style;

	public void setVectors(VectorGroup v) {
		vector = v;
	}
	
	public void setStyle() {
		style = convertStyle(styleString);
	}

	public void draw(String id, VectorGraphics vg) {
		for(Vector v: vector.getVectors()) {

			if(v instanceof TextRegion) {
				TextRegion tr = (TextRegion) v;
				tr.setText(text);
				vg.draw(id, tr.getBound().getShape(), tr.getBound().getStyle());
			}
			vg.draw(id, v.getShape(), v.getStyle());
		}
	}
	
	public Shape getShape() {
		return null;
	}
	
	public Map<String, String> getStyle() {
		return style;
	}
	
	private Map<String, String> convertStyle(String s) {
		
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
