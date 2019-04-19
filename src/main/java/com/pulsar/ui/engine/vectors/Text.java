package ui.engine.vectors;

import java.awt.Font;
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
	
	private static Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 40);
	private static FontRenderContext frc = new FontRenderContext(null, true, true);

	public GeneralPath getShape() {
		
		text = text.replaceAll("\\s+","");
		
		String t = text;
		
		if(t.startsWith("@")) {
			t = parameters.get(new QName(t.substring(1))).toString();
		}
		
		GlyphVector v = font.createGlyphVector(frc, t);
		GeneralPath p = (GeneralPath) v.getOutline();
		
		return p;
		
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
	
}
