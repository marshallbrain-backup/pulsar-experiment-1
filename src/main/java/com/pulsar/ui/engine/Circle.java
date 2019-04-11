package ui.engine;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "circle")
public class Circle implements Vector, Cloneable {
		
	private int renderX;
	private int renderY;
	private int renderRadius;

	@XmlAttribute(name = "cx")
	private long centerX;
	@XmlAttribute(name = "cy")
	private long centerY;
	@XmlAttribute(name = "r")
	private long radius;

	@XmlAttribute(name = "fill")
	private String fill;
	@XmlAttribute(name = "fill-opacity")
	private String fillOpacity;
	
	private Color fillColor;

	public Circle() {
	}
	
	public Circle(Color fill, long cx, long cy, long r) {
		init(fill, cx, cy, r);
	}
	
	private void init(Color f, long cx, long cy, long r) {
		
		fillColor = f;
		
		centerX = cx;
		centerY = cy;
		radius = r;
		
	}

	public int getCenterX() {
		return renderX;
	}
	
	public int getCenterY() {
		return renderY;
	}
	
	public long getRadius() {
		return renderRadius;
	}
	
	@Override
	public Object clone() {
		
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}  
	
	@Override
	public String getType() {
		return "circle";
	}
	
	@Override
	public Color getFillColor() {
		
		if(fillColor == null) {
			
			System.out.println(fill);
			System.out.println(fillOpacity);
			
			if(fill.startsWith("#")) {
				fill = fill.substring(1);
			}
			
			int r = Integer.parseInt(fill.substring(0, 2), 16);
			int g = Integer.parseInt(fill.substring(2, 4), 16);
			int b = Integer.parseInt(fill.substring(4, 6), 16);
			int a = Math.round(Float.parseFloat(fillOpacity)*255);
			
			fillColor = new Color(r, g, b, a);
			
		}
		
		return fillColor;
		
	}
	
	@Override
	public void move(Point o) {
		centerX += o.getX();
		centerY += o.getY();
	}

	@Override
	public void transform(double offset) {
		radius += offset;
	}
	
	@Override
	public void normalize(long screenSize, int screenWidth, int minSize) {
		
		int r = Math.toIntExact(convert(radius, screenSize/2, screenWidth/2));
		int x = Math.toIntExact(convert(centerX, screenSize, screenWidth));
		int y = Math.toIntExact(convert(centerY, screenSize, screenWidth));
		
		if(r < minSize) {
			r = minSize;
		}
		
		renderX = x;
		renderY = y;
		renderRadius = r;
		
	}
	
	@Override
	public Shape getShape() {
		return new Ellipse2D.Double(renderX-renderRadius, renderY-renderRadius, renderRadius*2, renderRadius*2);
	}
	
	private long convert(double value, double fromRefrence, double toRefrence) {
		return Math.round(((value/fromRefrence)*toRefrence));
	}
	
}
