package ui.engine.vectors;

import java.awt.Shape;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

import ui.engine.Point;
import ui.engine.VectorGraphics;

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
	
	private VectorGroup vector;
	
	private Map<QName, Object> parameters;
	
	public void setLink(Map<String, VectorGroup> vectorList) {
		
		location = location.replaceAll("\\s+","");
		vector = vectorList.get(location);
		
//		for(String s: par.split(";")) {
//			
//			String v = s;
//			
//			if(s.contains("@")) {
//				
//				v = s.substring(1);
//				Object o = parameters.get(new QName(v.split("\\.")[0]));
//				
//				if(v.contains(".")) {
//					for(String i: v.substring(v.indexOf(".")+1).split("\\.")) {
//						try {
//							Method m = o.getClass().getMethod(i);
//							o = m.invoke(o);
//						} catch (NoSuchMethodException | SecurityException e1) {
//							e1.printStackTrace();
//						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
//							e1.printStackTrace();
//						}
//					}
//				}
//				
//				e.add(o);
//				
//			} else {
//				e.add(v);
//			}
//			
//		}
		
	}
	
	public Point getOffset() {
		return new Point(x, y);
	}
	
	@Override
	public String getType() {
		return "link vector";
	}
	
	@Override
	public void draw(VectorGraphics vg) {
		
		par = par.replaceAll("\\s+","");
		List<Object> e = new ArrayList<Object>();
		
		for(String s: par.split(";")) {
			
			String v = s;
			
			if(s.contains("@")) {
				
				v = s.substring(1);
				Object o = parameters.get(new QName(v.split("\\.")[0]));
				
				if(v.contains(".")) {
					for(String i: v.substring(v.indexOf(".")+1).split("\\.")) {
						try {
							Method m = o.getClass().getMethod(i);
							o = m.invoke(o);
						} catch (NoSuchMethodException | SecurityException e1) {
							e1.printStackTrace();
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
							e1.printStackTrace();
						}
					}
				}
				
				e.add(o);
				
			} else {
				e.add(v);
			}
			
		}

		VectorGroup v = vector.clone();
		
		v.assingParameters(e.toArray());
		v.draw(vg);
		
	}

	@Override
	public void setStyle() {
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
		parameters = p;
	}
	
	@Override
	public Vector clone() {
		
		try {
			LinkVector clone = (LinkVector) super.clone();
			clone.parameters = new HashMap<QName, Object>(parameters);
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
