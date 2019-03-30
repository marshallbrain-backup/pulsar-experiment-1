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
		
		switch(v.getType()) {
		case "circle":
			drawCircle((Circle) v);
			break;
		}
		
	}
	
	public void drawCircle(Circle c) {
		graphics.fillOval(c.getCenterX()-c.getRadius(), c.getCenterY()-c.getRadius(), c.getRadius()*2, c.getRadius()*2);
	}

}
