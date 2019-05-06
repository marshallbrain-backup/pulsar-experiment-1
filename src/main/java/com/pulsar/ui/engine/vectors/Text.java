package ui.engine.vectors;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.GeneralPath;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;

@XmlRootElement(name = "text")
public class Text {

	@XmlAttribute(name = "style")
	private String styleString;
	@XmlValue
	private String text;

	private Map<String, String> style;
	private Map<QName, Object> parameters;
	
	//TODO make font dynamic
	//https://docs.oracle.com/javase/tutorial/2d/text/fonts.html
	private static Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
	private Graphics2D g2d;

	public GeneralPath getShape() {
		
		text = text.replaceAll("\\s+","");
		
		String t = text;
		
		if(t.startsWith("@")) {
			t = parameters.get(new QName(t.substring(1))).toString();
		}
		
		GlyphVector v = font.createGlyphVector(g2d.getFontMetrics(font).getFontRenderContext(), t);
		GeneralPath p = (GeneralPath) v.getOutline(0, 0);
		
		return p;
		
	}
	
	@XmlTransient
	public void setText(String t) {
		text = t;
	}
	
	public String getText() {
		return text;
	}
	
	public Font getFont() {
		return font;
	}

	@XmlTransient
	public Map<String, String> getStyle() {
		return style;
	}
	
	public String getStyleString() {
		return styleString;
	}
	
	public void setStyle(Map<String, String> s) {
		style = new HashMap<String, String>(s);
	}

	public void assingParamerters(Map<QName, Object> p) {
		parameters = p;
	}

	public void setCurrentGraphics(Graphics2D g) {
		g2d = g;
	}
	
}
