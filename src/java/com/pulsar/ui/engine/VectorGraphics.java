package ui.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map;

import pulsar.Main;

public class VectorGraphics {
	
	private Graphics2D graphics;
	private Point screenPosition;

	public VectorGraphics(Graphics g) {
		graphics = (Graphics2D) g;
		screenPosition = new Point(0, 0);
	}
	
	public Graphics getGraphics() {
		return graphics;
	}

	public void translationSet(ScreenPosition pos) {
		switch(pos) {
		case CENTER:
			Point newPos = new Point(Main.WIDTH/2, Main.HEIGHT/2);
			graphics.translate(newPos.getX()-screenPosition.getX(), newPos.getY()-screenPosition.getY());
			break;
		default:
			break;
		}
	}

	public void draw(Vector v) {
		
//		String type = map.get("type");
//		String fill = map.get("fill");
//		String fillOpacity = map.get("fill-opacity");
//		String cx = map.get("cx");
//		String cy = map.get("cy");
//		
//		if(type == null)
//			return;
//		if(fill == null)
//			fill = "0";
//		if(fillOpacity == null)
//			fillOpacity = "1";
//		if(cx == null)
//			cx = "0";
//		if(cy == null)
//			cy = "0";
//		
//		int radius = 50;
//		
//		graphics.setColor(toColor(fill, Float.parseFloat(fillOpacity)));
//		
//		switch(type) {
//		case "circle":
//			
//			break;
//		}
		
	}
	
	private Color toColor(String hex, float opacity) {
		
		int r = Integer.parseInt(hex.substring(0, 2), 16);
		int g = Integer.parseInt(hex.substring(2, 4), 16);
		int b = Integer.parseInt(hex.substring(4, 6), 16);
		int a = Math.round(opacity*255);
		
		return new Color(r, g, b, a);
		
	}
	
	public void drawCircle(Circle c) {
		graphics.fillOval(c.getCenterX()-c.getRadius(), c.getCenterY()-c.getRadius(), c.getRadius()*2, c.getRadius()*2);
	}

}
