package ui.engine;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;

import math.Other;
import pulsar.Main;
import ui.engine.Point;
import ui.engine.vectors.Vector;

public class VectorGraphics {
	
	private Graphics2D graphics;
	private Graphics2D graphicsOriginal;
	
	private Area areaLog;

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

	public void translationSet(Point o) {
		translationSet(ScreenPosition.ZERO);
		translationMove(o);
	}

	public void translationSet(ScreenPosition pos) {
		
		Point p;
		
		switch(pos) {
			case CENTER:
				p = new Point(Main.WIDTH/2, Main.HEIGHT/2);
				break;
			case ZERO:
				p = new Point(0, 0);
				break;
			default:
				return;
		}
		
		graphics = (Graphics2D) graphicsOriginal.create();
		graphics.translate(p.getX(), p.getY());
		
		init(graphics);
		
	}

	public void translationMove(Point o) {
		graphics.translate(o.getX(), o.getY());
	}

	public void draw(Vector v) {
		
		Area a = getArea(v, Main.WIDTH, Main.HEIGHT);
		
		if(a == null) {
			return;
		}
		
		String fill = v.getStyle().get("fill");
		if(!(fill == null || fill.equals("none"))) {
			
			String alpha = v.getStyle().get("fill-opacity");
			if(alpha == null)
				alpha = "1";
			
			graphics.setColor(Other.getColor(fill, alpha));
			graphics.fill(a);
			
		}
		
		String stroke = v.getStyle().get("stroke");
		if(!(stroke == null || stroke.equals("none"))) {
			
			String alpha = v.getStyle().get("stroke-opacity");
			if(alpha == null)
				alpha = "1";
			
			String width = v.getStyle().get("stroke-width");
			if(width == null)
				width = "1";
			BasicStroke style = new BasicStroke(Integer.parseInt(width));
			
			graphics.setColor(Other.getColor(stroke, alpha));
			graphics.setStroke(style);
			graphics.draw(a);
			
		}
		
	}

	public Area getArea(Vector v, int width, int height) {
		
		if(v.getShape() == null) {
			return null;
		}
		
		Area a = new Area(v.getShape());
		Area b = new Area(new Rectangle2D.Double(0, 0, width, height));
		try {
			b.transform(graphics.getTransform().createInverse());
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		
		a.intersect(b);
		if(areaLog != null && !a.isEmpty()) {
			areaLog.add(a);
		}
		
		return a;
		
	}
	
	public void startLogArea() {
		areaLog = new Area();
	}
	
	public Area stopLogArea() {
		Area a = (Area) areaLog.clone();
		a.transform(graphics.getTransform());
		areaLog.reset();
		if(!a.isEmpty()) {
			return a;
		}
		return null;
	}

}
