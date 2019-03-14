package gfx;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class Draw {
	
	public static BufferedImage circle(int r, int c) {
		return circle(r, c, 0, false);
	}
	
	public static BufferedImage circle(int r, int c, int s) {
		return circle(r, c, s, false);
	}
	
	public static BufferedImage circle(int r, int c, boolean f) {
		return circle(r, c, 0, f);
	}

	/**
	 * draws a circle using graphicts2d
	 * 
	 * @param radius radius of the circle
	 * @param color color of the circle
	 * @param stroke the stroke size of the bounds of the circle, adds 1 atomaticly
	 * @param fill is the circle filled
	 * 
	 * @return the bufferedImage contaning the circle
	 */
	public static BufferedImage circle(int r, int c, int s, boolean f) {
		
		s++;
		
		BufferedImage circle = new BufferedImage((r+s)*2, (r+s)*2, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = circle.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(new Color(c));
		g2d.setStroke(new BasicStroke(s));
		
		if(f) g2d.fillOval(s, s, circle.getWidth()-s*2, circle.getHeight()-s*2);
		else  g2d.drawOval(s, s, circle.getWidth()-s*2, circle.getHeight()-s*2);
		g2d.dispose();
		
		return circle;
		
	}
	
	public static BufferedImage rec(int x, int y, int c) {
		return null;
	}

}
