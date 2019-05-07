package ui.engine.vectors;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import ui.engine.Point;
import ui.engine.ScreenPosition;
import ui.engine.VectorGraphics;

@XmlRootElement(name = "tab_item")
public class TabItem {
	
	@XmlAttribute(name = "style")
	private String styleString;
	@XmlAttribute(name = "text")
	private String text;
	private String id;
	private String view;
	
	private VectorGroup vector;

	private List<Integer> offset;
	private Map<String, String> style;

	public void setVectors(VectorGroup v) {
		vector = v;
	}
	
	public String getId() {
		return id;
	}
	
	public void setStyle() {
		style = convertStyle(styleString);
	}

	public int draw(String i, int pos, int anchor, VectorGraphics vg) {
		
		id = i;
		vg.translationMove(new Point(pos, 0));
		for(Vector v: vector.getVectors()) {

			if(v instanceof TextRegion) {
				
				TextRegion tr = (TextRegion) v;
				tr.setText(text);
				//TODO change the draw method to have id parameter and remove setCurrentGraphics
				tr.setCurrentGraphics(vg.getGraphics());
				tr.setAnchor(anchor);
				Shape b = tr.getBound().getShape();
				vg.draw(id, b, tr.getBound().getStyle());
				pos += b.getBounds().getWidth();
				
			}
			
			vg.draw(id, v.getShape(), v.getStyle());
			
		}
		
		return pos;
		
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

	@XmlAttribute(name = "view")
	public String getView() {
		return view;
	}

	public void setView(String v) {
		view = v;
	}
	
}
