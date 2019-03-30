package ui.engine;

import java.awt.Color;
import java.util.Map;

import math.Match;

public class Circle implements Vector {

	private int centerX;
	private int centerY;
	private int radius;
	
	private Color fillColor;
	
	public Circle(Map<String, String> map) {
		String fill = map.get("fill");
		String fillOpacity = map.get("fill-opacity");
		String cx = map.get("cx");
		String cy = map.get("cy");
		
		if(fill == null && Match.isInt(fill, 16))
			fill = "0";
		if(fillOpacity == null && Match.isDouble(fill))
			fillOpacity = "1";
		if(cx == null && Match.isInt(fill))
			cx = "0";
		if(cy == null && Match.isInt(fill))
			cy = "0";
		
		init(fill, Float.parseFloat(fillOpacity), Integer.parseInt(cx), Integer.parseInt(cy), 0);
		
	}
	
	public Circle(int cx, int cy, int r) {
		init("0", 1, cx, cy, r);
	}
	
	private void init(String fc, float fa, int cx, int cy, int r) {
		
		fillColor = toColor(fc, fa);
		
		centerX = cx;
		centerY = cy;
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
		return centerX;
	}
	
	public int getCenterY() {
		return centerY;
	}
	
	public int getRadius() {
		return radius;
	}

	public void setRadius(int r) {
		radius = r;
	}

	@Override
	public String getType() {
		return "circle";
	}
	
}
