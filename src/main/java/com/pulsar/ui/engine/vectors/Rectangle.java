package ui.engine.vectors;

import java.awt.Shape;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

import math.Other;
import ui.engine.Point;

@XmlRootElement(name = "rect")
public class Rectangle implements Vector {
	
	@XmlAttribute(name = "y")
	private int baseX;
	@XmlAttribute(name = "x")
	private int baseY;
	@XmlAttribute(name = "width")
	private int baseWidth;
	@XmlAttribute(name = "height")
	private int baseHeight;
	
	private int renderX;
	private int renderY;
	private int renderWidth;
	private int renderHeight;
	
	private long cornerX;
	private long cornerY;
	private long width;
	private long height;
	
	@XmlAttribute(name = "style")
	private String styleString;
	
	private Map<String, String> style;
	private Map<QName, Object> parameters;
	
	public Rectangle() {
	}
	
	public Rectangle(String s, long x, long y, long w, long h) {
		init(s, x, y, w, h);
	}
	
	private void init(String s, long x, long y, long w, long h) {
		
		style = Other.convertStyle(s);
		
		cornerX = x;
		cornerY = y;
		width = w;
		height = h;
		
	}

	@Override
	public String getType() {
		return "rectangle";
	}

	@Override
	public Map<String, String> getStyle() {
		return style;
	}

	@Override
	public String getStyleString() {
		return styleString;
	}

	@Override
	public void setStyle(Map<String, String> s) {
		style = new HashMap<String, String>(s);
	}

	@Override
	public Shape getShape() {
		
		int w = baseWidth+renderWidth;
		int h = baseHeight+renderHeight;
		int x = baseX+renderX;
		int y = baseY+renderY;
		
		return new java.awt.Rectangle.Double(y, x, w, h);
		
	}

	@Override
	public void move(Point o) {
		cornerX += o.getX();
		cornerY += o.getY();
	}

	@Override
	public void transform(Point offset) {
		width += offset.getXInt();
		height += offset.getYInt();
	}

	@Override
	public void normalize() {
		
		renderX = Math.toIntExact(cornerX);
		renderY = Math.toIntExact(cornerY);
		renderWidth = Math.toIntExact(width);
		renderHeight = Math.toIntExact(height);
		
	}
	
	@Override
	public void normalize(long screenSize, int screenWidth, int minSize) {
		
		int w = Math.toIntExact(convert(width, screenSize, screenWidth));
		int h = Math.toIntExact(convert(height, screenSize, screenWidth));
		int x = Math.toIntExact(convert(cornerX, screenSize, screenWidth));
		int y = Math.toIntExact(convert(cornerY, screenSize, screenWidth));
		
		if(w < minSize) {
			w = minSize;
		}
		if(h < minSize) {
			h = minSize;
		}
		
		renderX = x;
		renderY = y;
		renderWidth = w;
		renderHeight = h;
		
	}

	@Override
	public void assingParamerters(Map<QName, Object> p) {
		parameters = p;
	}
	
	@Override
	public Object clone() {
		
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
