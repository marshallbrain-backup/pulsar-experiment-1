package ui.engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import pulsar.Main;

public class VectorGraphics {
	
	private Graphics2D graphics;
	private Point screenPosition;

	public VectorGraphics(Graphics g) {
		graphics = (Graphics2D) g;
		screenPosition = new Point(0, 0);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	public Graphics getGraphics() {
		return graphics;
	}

	public void translationSet(ScreenPosition pos) {
		switch(pos) {
		case CENTER:
			Point newPos = new Point(Main.WIDTH/2, Main.HEIGHT/2);
			graphics.translate(newPos.getX()-screenPosition.getX(), newPos.getY()-screenPosition.getY());
			screenPosition = newPos;
			break;
		default:
			break;
		}
	}

	public void draw(Vector v) {
		
		graphics.setColor(v.getFillColor());
		
		switch(v.getType()) {
		case "circle":
			drawCircle((Circle) v);
			break;
		}
		
	}
	
	public void drawCircle(Circle c) {
		int cx = c.getCenterX();
		int cy = c.getCenterY();
		int r = Math.toIntExact(c.getRadius());
		graphics.fillOval(cx-r, cy-r, r*2, r*2);
	}

}
