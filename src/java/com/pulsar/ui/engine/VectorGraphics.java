package ui.engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import pulsar.Main;
import ui.engine.Point;

public class VectorGraphics {
	
	private int[] screenBounds;
	
	private Graphics2D graphics;
	private Graphics2D graphicsOriginal;

	public VectorGraphics(Graphics g) {
		
		graphicsOriginal = (Graphics2D) g;
		
		graphics = (Graphics2D) graphicsOriginal.create();
		
		screenBounds = new int[] {-Main.WIDTH/2, -Main.HEIGHT/2, Main.WIDTH/2, Main.HEIGHT/2};
		
		init(graphics);
		
	}
	
	private void init(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	public Graphics getGraphics() {
		return graphics;
	}

	public void translationSet(ScreenPosition pos) {
		switch(pos) {
		case CENTER:
			Point center = new Point(Main.WIDTH/2, Main.HEIGHT/2);
			graphics = (Graphics2D) graphicsOriginal.create();
			graphics.translate(center.getX(), center.getY());
			break;
		default:
			break;
		}
		
		init(graphics);
		
	}

	public void translationMove(Point o) {
		graphics.translate(o.getX(), o.getY());
	}

	public void draw(Vector v) {
		draw(v, screenBounds[0], screenBounds[1], screenBounds[2], screenBounds[3]);
	}
	
	public void draw(Vector v, int minX, int minY, int maxX, int maxY) {
		
		graphics.setColor(v.getFillColor());
		
		Area a = new Area(v.getShape());
		Area b = new Area(new Rectangle2D.Double(minX, minY, maxX-minX, maxY-minY));
		
		a.intersect(b);
		
		graphics.fill(a);
		
	}

}
