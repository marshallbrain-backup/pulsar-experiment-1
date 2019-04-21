package ui.engine.vectors;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

import math.Other;
import ui.engine.Point;
import ui.engine.VectorGraphics;

@XmlRootElement(name = "circle")
public class Circle implements Vector {
	
	@XmlAttribute(name = "cx")
	private int baseX;
	@XmlAttribute(name = "cy")
	private int baseY;
	@XmlAttribute(name = "r")
	private int baseR;
		
	private int renderX;
	private int renderY;
	private int renderRadius;

	private long centerX;
	private long centerY;
	private long radius;

	@XmlAttribute(name = "style")
	private String styleString;
	@XmlAttribute(name = "id")
	private String id;
	@XmlAttribute(name = "on_click")
	private String onClick;
	
	private Map<String, String> style;

	public Circle() {
	}
	
	public Circle(String s, long cx, long cy, long r) {
		init(s, cx, cy, r);
	}
	
	private void init(String s, long cx, long cy, long r) {
		
		setStyle();
		
		centerX = cx;
		centerY = cy;
		radius = r;
		
	}

//	public int getCenterX() {
//		return renderX;
//	}
//	
//	public int getCenterY() {
//		return renderY;
//	}
//	
//	public long getRadius() {
//		return renderRadius;
//	}
	
	@Override
	public String getType() {
		return "circle";
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getAction(String action) {
		switch(action) {
			case "right click":
				return onClick;
			default:
				return null;
		}
	}
	
	@Override
	public void draw(VectorGraphics vg) {
		vg.draw(id, getShape(), getStyle());
	}

	@Override
	public Map<String, String> getStyle() {
		return style;
	}

	@Override
	public void setStyle() {
		style = convertStyle(styleString);
	}
	
	@Override
	public void move(Point o) {
		centerX += o.getX();
		centerY += o.getY();
	}

	@Override
	public void transform(Point offset) {
		radius += offset.getX();
	}

	@Override
	public void normalize() {
		
		renderX = Math.toIntExact(centerX);
		renderY = Math.toIntExact(centerY);
		renderRadius = Math.toIntExact(radius);
		
	}
	
	@Override
	public void normalize(long screenSize, int screenWidth, int minSize) {
		
		int r = Math.toIntExact(convert(radius, screenSize/2, screenWidth/2));
		int x = Math.toIntExact(convert(centerX, screenSize, screenWidth));
		int y = Math.toIntExact(convert(centerY, screenSize, screenWidth));
		
		if(r < minSize) {
			r = minSize;
		}
		
		renderX = x;
		renderY = y;
		renderRadius = r;
		
	}
	
	@Override
	public Shape getShape() {
		
		int r = baseR+renderRadius;
		int x = baseX+(renderX-r);
		int y = baseY+(renderY-r);
		
		return new Ellipse2D.Double(x, y, r*2, r*2);
		
	}

	@Override
	public void assingParamerters(Map<QName, Object> p) {
	}
	
	@Override
	public Vector clone() {
		
		try {
			Circle clone = (Circle) super.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
