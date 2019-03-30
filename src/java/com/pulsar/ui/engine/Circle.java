package ui.engine;

import java.awt.Color;
import java.util.Map;

import math.Match;

public class Circle implements Vector {

	private int centerX;
	private int centerY;
	
	private long radiusOffset;
	
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
		init("0", 1, cx, cy, r);
	}
	
	private void init(String fc, float fa, long cx, long cy, long r) {
		
		fillColor = toColor(fc, fa);
		
		centerX = Math.toIntExact(cx);
		centerY = Math.toIntExact(cy);
		radiusOffset = r;
		
	}
	
	private Color toColor(String hex, float alpha) {
		
		int r = Integer.parseInt(hex.substring(0, 2), 16);
		int g = Integer.parseInt(hex.substring(2, 4), 16);
		int b = Integer.parseInt(hex.substring(4, 6), 16);
		int a = Math.round(alpha*255);
		
		return new Color(r, g, b, a);
		
	}
	
	public int getCenterX() {
		return centerX;
	}
	
	public int getCenterY() {
		return centerY;
	}
	
	public long getRadius() {
		return radiusOffset;
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
	public Vector transform(long distance, double angle, long radius, long screenSize, int screenWidth, long minSize) {
		long r = Math.toIntExact(radiusOffset + Math.round((((double) radius/screenSize)*screenWidth/2)));
		if(r < minSize) {
			r = minSize;
		}
		return new Circle(Math.round(Math.cos(angle)*distance), Math.round(Math.sin(angle)*distance), r);
	}
	
}
