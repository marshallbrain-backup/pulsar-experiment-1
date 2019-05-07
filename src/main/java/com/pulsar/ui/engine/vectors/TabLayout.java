package ui.engine.vectors;

import java.awt.Shape;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

import ui.engine.Point;
import ui.engine.VectorGraphics;

@XmlRootElement(name = "tab_layout")
public class TabLayout implements Vector {
	
	@XmlAttribute(name = "x")
	private int y;
	@XmlAttribute(name = "y")
	private int x;
	@XmlAttribute(name = "anchor")
	private int anchor;
	
	@XmlAttribute(name = "id")
	private String id;
	
	@XmlAnyElement(lax = true)
	private List<TabItem> tabs;
	
	@Override
	public void setVectors(Map<String, VectorGroup> vectorList) {
		
		VectorGroup s = vectorList.get("view.tab.static");
		
		for(TabItem t: tabs) {
			t.setVectors(s);
		}
	}
	
	public List<TabItem> getTabs() {
		return tabs;
	}
	
	@Override
	public String getType() {
		return "tab_layout";
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getAction(String id, String action) {
		switch(action) {
			case "right click":
				TabItem t = tabs.get(Integer.parseInt(id.split(" ")[1]));
				return "key setView " + t.getView();
			default:
				return null;
		}
	}

	@Override
	public Shape getShape() {
		return null;
	}

	@Override
	public Map<String, String> getStyle() {
		return null;
	}

	@Override
	public void inherit(Vector v) {
	}

	@Override
	public void draw(VectorGraphics vg) {
		int pos = 0;
		for(int i = 0; i < tabs.size(); i++) {
			pos = tabs.get(i).draw(id + " " + String.valueOf(i), pos, anchor, vg);
		}
	}

	@Override
	public void move(Point offset) {
	}

	@Override
	public void transform(Point offset) {
	}

	@Override
	public void normalize() {
	}

	@Override
	public void normalize(long screenSize, int screenWidth, int minSize) {
	}

	@Override
	public void setStyle() {
	}

	@Override
	public void assingParamerters(Map<QName, Object> p) {
	}
	
	@Override
	public Vector clone() {
		
		try {
			TabLayout clone = (TabLayout) super.clone();
			clone.tabs.clear();
			for(TabItem t: tabs) {
				clone.tabs.add(t);
			}
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
