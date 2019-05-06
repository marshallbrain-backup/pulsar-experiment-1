package ui.engine.vectors;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
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
	
	@XmlElement(name = "text")
	private Text text;
	@XmlAttribute(name = "id")
	private String id;
	
	@XmlAnyElement(lax = true)
	private Vector bound;
	private Vector calcBound;
	private Graphics2D g2d;
	
	public Vector getBound() {
		
		if(calcBound == null) {
			GeneralPath s = text.getShape();
			java.awt.Rectangle r = s.getBounds();
			FontMetrics f = g2d.getFontMetrics(text.getFont());
			double vx = f.stringWidth(text.getText())+padingX*2;
			double vy = r.getHeight()+padingY*2;
			
			Vector v = (Vector) bound.clone();
			String sw = v.getStyle().get("stroke-width");
			if(sw != null) {
				int strokeWidth = Integer.parseInt(sw);
				v.transform(new Point(strokeWidth, strokeWidth));
			}
			v.transform(new Point(vx, vy));
			v.move(new Point(x, y));
			v.normalize();
			calcBound = v.clone();
		}
		
		return calcBound;
		
	}
	
	public void setCurrentGraphics(Graphics2D g) {
		g2d = g;
		text.setCurrentGraphics(g2d);
	}

	@XmlTransient
	public String getText() {
		return text.getText();
	}

	public void setText(String t) {
		text.setText(t);
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
		getBound().draw(vg);
		vg.draw(id, getShape(), getStyle());
		calcBound = null;
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
		String sw = getBound().getStyle().get("stroke-width");
		if(sw != null) {
			int strokeWidth = Integer.parseInt(sw);
			at.translate(strokeWidth/2, 0);
		}
		at.translate(0, r.getMaxY());
		at.translate(0, r.getHeight());
		at.translate(padingX, padingY);
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

	@Override
	public void inherit(Vector v) {
		
		// TODO Auto-generated method stub
		
	}
	
}
