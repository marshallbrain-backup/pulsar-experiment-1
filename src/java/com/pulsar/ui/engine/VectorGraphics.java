package ui.engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import pulsar.Main;

public class VectorGraphics {
	
	private int[] screenBounds;
	
	private Graphics2D graphics;
	private Graphics2D graphicsOriginal;

	public VectorGraphics(Graphics g) {
		
		graphicsOriginal = (Graphics2D) g;
		graphics = (Graphics2D) graphicsOriginal.create();
		
		screenBounds = new int[] {-Main.WIDTH/2, -Main.HEIGHT/2, Main.WIDTH/2, Main.HEIGHT/2};
		
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
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
	}

	public void translationMove(Point o) {
		graphics.translate(o.getX(), o.getY());
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
		drawCircle(c, screenBounds[0], screenBounds[1], screenBounds[2], screenBounds[3]);
	}
	
	public void drawCircle(Circle c, int minX, int minY, int maxX, int maxY) {
		int cx = c.getCenterX();
		int cy = c.getCenterY();
		int r = Math.toIntExact(c.getRadius());
		List<Shape> arcs = drawVisibleArc(cx, cy, r, minX, minY, maxX, maxY);
		
		if(!arcs.isEmpty()) {
			Path2D cutCircle = new Path2D.Double();
			for(Shape a: arcs) {
				cutCircle.append(a, true);
			}
			cutCircle.closePath();
			graphics.fill(cutCircle);
		}
		
	}
	
	private List<Shape> drawVisibleArc(int cx, int cy, int r, int minX, int minY, int maxX, int maxY) {
		
		int[] lines = new int[] {maxX-cx, maxY-cy, minX-cx, minY-cy};
		List<Double> angles = new ArrayList<Double>();
		List<Shape> arcs = new ArrayList<Shape>();
		
		for(int i = 0; i < lines.length; i++) {
			
			int v = lines[i];
			double z = Math.sqrt(Math.pow(r, 2)-Math.pow(Math.abs(v), 2));
			
			if(!Double.isNaN(z)) {
				
				double startAngle = 0;
				double endAngle = 0;
				
				if(i%2 == 0) {
					if(v > lines[i+1]) {
						continue;
					}
					startAngle = Math.toDegrees(Math.atan2(z, v));
					endAngle = Math.toDegrees(Math.atan2(-z, v));
				} else {
					if(Math.abs(v) > Math.abs(lines[i-1])) {
						continue;
					}
					startAngle = Math.toDegrees(Math.atan2(v, z))+180;
					endAngle = Math.toDegrees(Math.atan2(v, -z))+180;
				}
				
				if(v < 0) {
					if(startAngle < 0) {
						startAngle += 360;
					}
					if(endAngle < 0) {
						endAngle += 360;
					}
				}
				
				angles.add(Math.min(startAngle, endAngle));
				angles.add(Math.max(startAngle, endAngle));
				
			}
			
		}
		
		for(int i = 1; i < angles.size(); i+=2) {
			
			double startAngle = 0;
			double endAngle = 0;
			
			startAngle = angles.get(i);
			if(i+1 >= angles.size()) {
				endAngle = angles.get(0);
			} else {
				endAngle = angles.get(i+1);
			}
			
			endAngle -= startAngle;
			
			if(endAngle < 0) {
				endAngle += 360;
			}
			
			Arc2D arc = new Arc2D.Double(cx-r, cy-r, r*2, r*2, startAngle, endAngle, Arc2D.OPEN);
			arcs.add(arc);
			
		}
		
		if(arcs.isEmpty()) {
			if((minX < cx-r && cx-r < maxX) && (minY < cy-r && cy-r < maxY)) {
				Arc2D arc = new Arc2D.Double(cx-r, cy-r, r*2, r*2, 0, 360, Arc2D.OPEN);
				arcs.add(arc);
			} else if((minX > cx-r && cx-r > maxX) && (minY > cy-r && cy-r > maxY)) {
				arcs.add(new Rectangle2D.Float(minX, minY, maxX-minX, maxY-minY));
			}
			
		}
		
		return arcs;
		
	}

}
