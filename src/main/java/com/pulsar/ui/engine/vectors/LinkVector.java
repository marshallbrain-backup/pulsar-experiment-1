package ui.engine.vectors;

import java.awt.Shape;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

import ui.engine.Point;

@XmlRootElement(name = "link_vector")
public class LinkVector implements Vector {
	
	@XmlAttribute(name = "x")
	private int x;
	@XmlAttribute(name = "y")
	private int y;
	
	@XmlAttribute(name = "link")
	private String vector;
	@XmlAttribute(name = "give")
	private String par;
	
	private Map<QName, Object> parameters;
	
	public String getLink() {
		return vector;
	}
	
	public Object[] getParamerters() {
		return parameters.values().toArray();
	}
	
	public Point getOffset() {
		return new Point(x, y);
	}
	
	@Override
	public String getType() {
		return "link vector";
	}

	@Override
	public String getStyleString() {
		return null;
	}

	@Override
	public Shape getShape() {
		return null;
	}

	@Override
	public Map<String, String> getStyle() {
		return null;
	}

	@Override
	public void move(Point offset) {
	}

	@Override
	public void transform(double offset) {
	}

	@Override
	public void normalize(long screenSize, int screenWidth, int minSize) {
	}

	@Override
	public void setStyle(Map<String, String> s) {
	}

	@Override
	public void assingParamerters(Map<QName, Object> p) {
		parameters = p;
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
	
}
