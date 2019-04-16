package ui.engine.vectors;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
	private String location;
	@XmlAttribute(name = "give")
	private String par;
	
	private Map<QName, Object> parameters;
	
	public Object[] getLink() {
		
		location = location.replaceAll("\\s+","");
		par = par.replaceAll("\\s+","");
		List<Object> e = new ArrayList<Object>();
		
		e.add(location);
		
		if(par.contains("@")) {
			for(int i = par.indexOf("@", 0); i != -1; i = par.indexOf("@", 0)) {
				String v = par.substring(i+1, par.indexOf(";", i));
				e.add(parameters.get(new QName(v)));
				par = par.replace("@"+v+";", "");
			}
		}
		
		e.addAll(Arrays.asList(par.split(";")));
		
		return e.toArray();
		
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
