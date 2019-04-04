package ui.engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import pulsar.Main;
import ui.engine.Point;

public class VectorGraphics {
	
	private Graphics2D graphics;
	private Graphics2D graphicsOriginal;

	public VectorGraphics(Graphics g) {
		
		graphicsOriginal = (Graphics2D) g;
		
		graphics = (Graphics2D) graphicsOriginal.create();
		
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
		Area a = getArea(v, Main.WIDTH, Main.HEIGHT);
		graphics.draw(a);
	}

	public void fill(Vector v) {
		Area a = getArea(v, Main.WIDTH, Main.HEIGHT);
		graphics.fill(a);
	}
	
	public Area getArea(Vector v, int width, int height) {
		
		graphics.setColor(v.getFillColor());
		
		Area a = new Area(v.getShape());
		Area b = new Area(new Rectangle2D.Double(0, 0, width, height));
		try {
			b.transform(graphics.getTransform().createInverse());
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		
		a.intersect(b);
		
		return a;
		
	}

}
