package ui.engine.vectors;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;

import ui.engine.Point;
import ui.engine.VectorGraphics;
import ui.engine.scripts.token.Token;
import ui.engine.scripts.token.TokenType;

@XmlRootElement(name = "path")
public class Path implements Vector {
	
	@XmlAttribute(name = "y")
	private int baseX;
	@XmlAttribute(name = "x")
	private int baseY;
	
	private int renderX;
	private int renderY;
	
	private long cornerX;
	private long cornerY;
	
	@XmlAttribute(name = "style")
	private String styleString;
	@XmlAttribute(name = "id")
	private String id;
	@XmlAttribute(name = "p")
	private String stringPath;
	
	private GeneralPath gPath;
	
	private List<Segment> segments;
	private List<Segment> segList;
	private Map<String, String> style;

	@Override
	public void init() {
		
		GeneralPath g = new GeneralPath();
		
		List<Segment> segs = new ArrayList<Segment>();
		
		Pattern number = Pattern.compile("^[-]?[0-9]+[\\.]?[0-9]*[\\p{Space}[,]]?[-]?[0-9]*[\\.]?[0-9]*");
		Pattern letter = Pattern.compile("^[a-zA-Z]");
		Pattern space = Pattern.compile("^[\\p{Space}]");
		
		String str = stringPath + "";
		Segment seg = null;
		while(!str.isEmpty()) {
			
			Matcher m;
			
			m = letter.matcher(str);
			if(m.find()) {
				
				seg = new Segment();
				segs.add(seg);
				
				seg.setType(m.group());
				str = str.substring(m.end());
				
			}
			
			m = space.matcher(str);
			if(m.find()) {
				str = str.substring(m.end());
			}
			
			m = number.matcher(str);
			if(m.find()) {

				String s = m.group();
				String[] n = s.split("[\\p{Space}[,]]");
				seg.setX(Double.parseDouble(n[0]));
				if(n.length == 2) {
					seg.setY(Double.parseDouble(n[1]));
				}
				
				str = str.substring(m.end());
				
			}
			
			m = space.matcher(str);
			if(m.find()) {
				str = str.substring(m.end());
			}
			
		}
		
		segments = new ArrayList<Segment>();
		segList = new ArrayList<Segment>();
		
		for(Segment s: segs) {
			segments.add(new Segment(s));
		}
		
		createSegmentList();
		
	}
	
	private void createSegmentList() {
		
		Segment last = new Segment();
		for(Segment s: segments) {
			s = new Segment(s, last);
			segList.add(s);
			last = s;
		}
		
	}

	@Override
	public String getType() {
		return "path";
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
	public Shape getShape() {
		
		GeneralPath gp = new GeneralPath();
		
		if(segList == null) {
			createSegmentList();
		}
		
		for(Segment s: segList) {
			
			switch (s.getType().toUpperCase()) {
				
				case "M":
					gp.moveTo(s.getX(), s.getY());
					break;
				case "L":
					gp.lineTo(s.getX(), s.getY());
					break;
				case "Z":
					gp.closePath();
					break;
				
				default:
					System.out.println("you broke something");
					break;
					
			}
			
		}
		
		return gp;
	}

	@Override
	public Map<String, String> getStyle() {
		return style;
	}

	@Override
	public void inherit(Vector v) {
	}

	@Override
	public void draw(VectorGraphics vg) {
		vg.draw(id, getShape(), getStyle());
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
	public void setStyle() {
		style = convertStyle(styleString);
	}

	@Override
	public void assingParamerters(Map<QName, Object> p) {
	}

	@Override
	public Vector clone() {
		
		try {
			Path clone = (Path) super.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
