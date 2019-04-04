package ui.engine;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Map;

import bodys.Body;
import math.Match;

public class Circle implements Vector {
		
	private int renderX;
	private int renderY;
	private int renderRadius;
	
	private long centerX;
	private long centerY;
	private long radius;
	
	private Color fillColor;
	
	public Circle(Map<String, String> map) {
		
		String fill = map.get("fill");
		String fillOpacity = map.get("fill-opacity");
		String cx = map.get("cx");
		String cy = map.get("cy");
		String r = map.get("r");
		
		if(fill == null || !Match.isInt(fill, 16))
			fill = "000000";
		if(fillOpacity == null || !Match.isDouble(fillOpacity))
			fillOpacity = "1";
		if(cx == null || !Match.isInt(cx))
			cx = "0";
		if(cy == null || !Match.isInt(cy))
			cy = "0";
		if(r == null || !Match.isInt(r))
			r = "0";
		
		init(fill, Float.parseFloat(fillOpacity), Integer.parseInt(cx), Integer.parseInt(cy), Long.parseLong(r));
		
	}
	
	public Circle(long cx, long cy, long r) {
		init("000000", 1, cx, cy, r);
	}
	
	public Circle(Color fill, long cx, long cy, long r) {
		init(fill, cx, cy, r);
	}
	
	private void init(String fc, float fa, long cx, long cy, long r) {
		init(toColor(fc, fa), cx, cy, r);
	}
	
	private void init(Color fill, long cx, long cy, long r) {
		
		fillColor = fill;
		
		centerX = Math.toIntExact(cx);
		centerY = Math.toIntExact(cy);
		radius = r;
		
	}
	
	private Color toColor(String hex, float alpha) {
		
		int r = Integer.parseInt(hex.substring(0, 2), 16);
		int g = Integer.parseInt(hex.substring(2, 4), 16);
		int b = Integer.parseInt(hex.substring(4, 6), 16);
		int a = Math.round(alpha*255);
		
		return new Color(r, g, b, a);
		
	}
	
	public int getCenterX() {
		return renderX;
	}
	
	public int getCenterY() {
		return renderY;
	}
	
	public long getRadius() {
		return renderRadius;
	}
	
	@Override
	public String getType() {
		return "circle";
	}
	
	@Override
	public Color getFillColor() {
		return fillColor;
	}

	@Override
	public void move(Point o, long screenSize, int screenWidth) {
		centerX = convert(o.getX(), screenWidth, screenSize);
		centerY = convert(o.getY(), screenWidth, screenSize);
		
	}

	@Override
	public void normalize(long screenSize, int screenWidth, int minSize) {
		
		int r = Math.toIntExact(convert(radius, screenSize, screenWidth/2));
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
	public Vector copy(Body b) {
		
		long d = b.getDistance();
		long r = b.getRadius();
		double a = b.getAngle();
		
		return new Circle(fillColor, Math.round(Math.sin(a)*d), Math.round(Math.sin(a)*d), r);
	}
	
	private long convert(double value, double fromRefrence, double toRefrence) {
		return Math.round(((value/fromRefrence)*toRefrence));
	}

	public Shape getCircle() {
		return new Ellipse2D.Double(renderX-renderRadius, renderY-renderRadius, renderRadius*2, renderRadius*2);
	}
	
}
