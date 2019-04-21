package ui.engine.vectors;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.namespace.QName;

import ui.engine.Point;
import ui.engine.VectorGraphics;

@XmlRootElement(name = "text_region")
public class TextRegion implements Vector {

	@XmlAttribute(name = "x")
	private int y;
	@XmlAttribute(name = "y")
	private int x;
	@XmlAttribute(name = "pading_x")
	private int padingX;
	@XmlAttribute(name = "pading_y")
	private int padingY;
	@XmlAttribute(name = "anchor")
	private int anchor;
	
	@XmlElement(name = "text")
	private Text text;
	@XmlAttribute(name = "id")
	private String id;
	
	@XmlAnyElement(lax = true)
	private Vector bound;
	
	
	public Vector getBound() {
		return (Vector) bound.clone();
	}
	
	@Override
	public String getType() {
		return "text region";
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getAction(String action) {
		switch(action) {
			default:
				return null;
		}
	}
	
	@Override
	public void draw(VectorGraphics vg) {
		
		GeneralPath s = text.getShape();
		java.awt.Rectangle r = s.getBounds();
		double vx = r.getWidth()+padingX*2;
		double vy = r.getHeight()+padingY*2;
		
		Vector v = (Vector) bound.clone();
		v.transform(new Point(vx, vy));
		v.move(new Point(x, y-vy));
		v.normalize();
		v.draw(vg);

		vg.draw(id, getShape(), getStyle());
		
	}

	@Override
	@XmlTransient
	public Map<String, String> getStyle() {
		return text.getStyle();
	}

	@Override
	public void setStyle() {
		text.setStyle(convertStyle(text.getStyleString()));
		bound.setStyle();
	}

	@Override
	public Shape getShape() {
		
		GeneralPath s = text.getShape();
		AffineTransform at = new AffineTransform();
		java.awt.Rectangle r = s.getBounds();
		at.translate(-r.getMinX(), -r.getMinY());
		at.translate(0, -r.getHeight());
		at.translate(padingX, -padingY);
		s.transform(at);
		
		return s;
		
	}
	
	@Override
	public void move(Point offset) {
	}
	
	@Override
	public void transform(Point offset) {
	}

	@Override
	public void normalize() {
	}
	
	@Override
	public void normalize(long screenSize, int screenWidth, int minSize) {
	}

	@Override
	public void assingParamerters(Map<QName, Object> p) {
		text.assingParamerters(p);
	}
	
	@Override
	public Vector clone() {
		
		try {
			TextRegion clone = (TextRegion) super.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
