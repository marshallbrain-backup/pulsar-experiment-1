package ui.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pulsar.Main;
import ui.engine.Point;

public class VectorGraphics {
	
	private int[] screenBounds;
	
	private Graphics2D graphics;
	private Graphics2D graphicsOriginal;

	public VectorGraphics(Graphics g) {
		
		graphicsOriginal = (Graphics2D) g;
		graphics = (Graphics2D) graphicsOriginal.create();
		
		screenBounds = new int[] {-Main.WIDTH/2+200, -Main.HEIGHT/2+200, Main.WIDTH/2-200, Main.HEIGHT/2-200};
		
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
		r = 200;
		List<Shape> arcs = drawVisibleArc(cx, cy, r, minX, minY, maxX, maxY);
		
		if(!arcs.isEmpty()) {
			Path2D cutCircle = new Path2D.Double();
			for(Shape a: arcs) {
				cutCircle.append(a, true);
				
			}
			cutCircle.closePath();
			graphics.fill(cutCircle);
		}
		
		graphics.setColor(Color.BLACK);
		graphics.drawRect(minX, minY, maxX-minX, maxY-minY);
		
	}
	
	private List<Shape> drawVisibleArc(int cx, int cy, int r, int minX, int minY, int maxX, int maxY) {
		
		int[] lines = new int[] {minX-cx, maxX-cx, minY-cy, maxY-cy};
		List<Double> angles = new ArrayList<Double>();
		List<Shape> arcs = new ArrayList<Shape>();
		
		getAngelIntersection(angles, 0, r, lines[0], lines[2], lines[3]);
		getAngelIntersection(angles, 1, r, lines[1], lines[2], lines[3]);
		getAngelIntersection(angles, 2, r, lines[2], lines[0], lines[1]);
		getAngelIntersection(angles, 3, r, lines[3], lines[0], lines[1]);
		
		double totalAngle = 0;
		
		for(int i = 0; i < angles.size(); i+=2) {
			
			double startAngle = angles.get(i);
			double endAngle = angles.get(i+1);
			
			double offset = endAngle - startAngle;
			
			totalAngle += Math.abs(offset);
			
		}
		
		if(totalAngle > 360) {
			Collections.sort(angles);
			angles.set(0, angles.get(0)+360);
		}

		Collections.sort(angles);
		
		for(int i = 0; i < angles.size(); i+=2) {
			
			double startAngle = angles.get(i);
			double endAngle = angles.get(i+1);
			
			double offset = endAngle - startAngle;
			
			Arc2D arc = new Arc2D.Double(cx-r, cy-r, r*2, r*2, startAngle, offset, Arc2D.OPEN);
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
	
	private void getAngelIntersection(List<Double> angles, int cor, int r, int l, int min, int max) {
		
		double z = Math.sqrt(Math.pow(r, 2)-Math.pow(Math.abs(l), 2));
		
		if(!Double.isNaN(z)) {
			
			double startAngle = 0;
			double endAngle = 0;
			
			startAngle = Math.toDegrees(Math.atan2(z, l));
			endAngle = Math.toDegrees(Math.atan2(-z, l));
			
			if(cor > 1) {
				startAngle -= 90;
				endAngle -= 90;
				z = -z;
			}
			if(cor%2 == 1) {
				endAngle += 360;
			}
			
			if((startAngle == 0 && endAngle == 0) || (startAngle == 0 && endAngle == 0)) {
				return;
			}
			
			if(insideBounds(-z, min, max))
				angles.add(startAngle);

			if(insideBounds(z, min, max))
				angles.add(endAngle);
			
		}
		
	}

	private boolean insideBounds(double z, int min, int max) {
		
		if(min <= z && z <= max) {
			return true;
		}
		
		return false;
		
	}

}
