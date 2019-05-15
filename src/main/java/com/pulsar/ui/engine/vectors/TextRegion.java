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

import files.VarName;
import ui.engine.Point;
import ui.engine.VectorGraphics;

@XmlRootElement(name = "text_region")
public class TextRegion implements Vector {

	@XmlAttribute(name = "x")
	private int x;
	@XmlAttribute(name = "y")
	private int y;
	@XmlAttribute(name = "pading_x")
	private int padingX;
	@XmlAttribute(name = "pading_y")
	private int padingY;
	@XmlAttribute(name = "height")
	private int height;
	@XmlAttribute(name = "anchor")
	private int anchor;
	
	@XmlElement(name = "text")
	private Text text;
	@XmlAttribute(name = "id")
	private String id;
	
	@XmlAnyElement(lax = true)
	private Vector bound;
	private Vector calcBound;
	private Graphics2D g2d;
	
	public void init() {
		text.init();
	}
	
	public Vector getBound() {
		
		if(height == 0) {
			height = (int) Math.ceil(text.getShape().getBounds2D().getHeight());
		}

		if(bound != null) {
			if(calcBound == null) {
				FontMetrics f = g2d.getFontMetrics(text.getFont());
				double vx = f.stringWidth(text.getText())+padingX*2;
				double vy = height+padingY*2;
				
				Vector v = (Vector) bound.clone();
				String sw = v.getStyle().get("stroke-width");
				int strokeWidth = 0;
				if(sw != null) {
					strokeWidth = Integer.parseInt(sw);
					v.transform(new Point(strokeWidth, strokeWidth));
				}
				
				int xOffset = 0;
				int yOffset = 0;
				switch(anchor) {
					case 0:
						break;
					case 1:
						break;
					case 2:
						yOffset = -Math.toIntExact(Math.round(vy)) - strokeWidth;
						break;
					case 3:
						break;
				}
				
				v.transform(new Point(vx, vy));
				v.move(new Point(x, y));
				v.move(new Point(xOffset, yOffset));
				v.normalize();
				calcBound = v.clone();
			}
		
			return calcBound;
		}
		
		return null;
		
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
	public String getAction(String id, String action) {
		switch(action) {
			default:
				return null;
		}
	}
	
	@Override
	public void draw(VectorGraphics vg, Map<String, Map<VarName, Object>> vars) {
		
		setCurrentGraphics(vg.getGraphics());
		
		Vector b = getBound();
		if(b != null) {
			b.move(new Point(x, y));
			b.draw(vg);
		}
		
		if(vars != null) {

			String t = text.getText();
			String v = t.split("_")[0];
			String k = "";
			if(!vars.containsKey(v)) {
				v = "";
				k = t;
			} else {
				k = t.substring(t.indexOf("_"));
			}
			
			text.setText(vars.get(v).get(VarName.valueOf(k)));
			
		}
		
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
		
		if(bound != null) {
			bound.setStyle();
		}
		
	}

	@Override
	public Shape getShape() {
		
		GeneralPath s = text.getShape();
		AffineTransform at = new AffineTransform();
		Vector b = getBound();
		int strokeWidth = 0;
		if(b != null) {
			String sw = b.getStyle().get("stroke-width");
			strokeWidth = Integer.parseInt(sw);
			at.translate(strokeWidth/2, -strokeWidth);
		}
		
		int xOffset = 0;
		int yOffset = 0;
		switch(anchor) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				yOffset = -Math.toIntExact(Math.round(height+padingY*2)) - strokeWidth;
				break;
			case 3:
				break;
		}
		
		at.translate(0, height);
		at.translate(padingX, padingY);
		at.translate(x, y);
		at.translate(xOffset, yOffset);
		s.transform(at);
		
		calcBound = null;
		
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
