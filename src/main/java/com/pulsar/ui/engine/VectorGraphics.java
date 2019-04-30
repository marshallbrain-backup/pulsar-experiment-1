package ui.engine;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import math.Other;
import pulsar.Main;
import ui.engine.Point;
import ui.engine.vectors.Vector;

public class VectorGraphics {
	
	private Graphics2D graphics;
	private Graphics2D graphicsOriginal;
	
	private Map<String, Area> areaLog;
	
	private AffineTransform affineTransform;

	public VectorGraphics(Graphics g) {
		
		graphicsOriginal = (Graphics2D) g;
		
		graphics = (Graphics2D) graphicsOriginal.create();
		
		init(graphics);
		
	}
	
	private void init(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	public Graphics2D getGraphics() {
		return graphics;
	}
	
	public void saveTransform() {
		affineTransform = graphics.getTransform();
	}
	
	public void revertTransform() {
		graphics.setTransform(affineTransform);
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

	public void draw(String id, Shape s, Map<String, String> m) {
		
		Area a = getArea(s, Main.WIDTH, Main.HEIGHT);
		
		if(a == null) {
			return;
		}
		
		if(areaLog != null && !a.isEmpty()) {
			Area t = a.createTransformedArea(graphics.getTransform());
			Area p = areaLog.putIfAbsent(id, t);
			if(p != null) {
				t.add(p);
				areaLog.put(id, t);
			}
		}
		
		if(m != null) {
			
			String fill = m.get("fill");
			if(!(fill == null || fill.equals("none"))) {
				
				String alpha = m.get("fill-opacity");
				if(alpha == null)
					alpha = "1";
				
				graphics.setColor(Other.getColor(fill, alpha));
				graphics.fill(a);
				
			}
			
			String stroke = m.get("stroke");
			if(!(stroke == null || stroke.equals("none"))) {
				
				String alpha = m.get("stroke-opacity");
				if(alpha == null)
					alpha = "1";
				
				String width = m.get("stroke-width");
				if(width == null)
					width = "1";
				BasicStroke bs = new BasicStroke(Integer.parseInt(width));
				
				graphics.setColor(Other.getColor(stroke, alpha));
				graphics.setStroke(bs);
				graphics.draw(a);
				
			}
			
		}
		
	}

	public Area getArea(Shape s, int width, int height) {
		
		if(s == null) {
			return null;
		}
		
		Area a = new Area(s);
		Area b = new Area(new Rectangle2D.Double(0, 0, width, height));
		try {
			b.transform(graphics.getTransform().createInverse());
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		
		a.intersect(b);
		
		return a;
		
	}
	
	public void startLogArea() {
		areaLog = new HashMap<String, Area>();
	}
	
	public Map<String, Area> stopLogArea() {
		Map<String, Area> a = new HashMap<String, Area>(areaLog);
		areaLog.clear();
		if(!a.isEmpty()) {
			return a;
		}
		return null;
	}

}
