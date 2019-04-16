package ui.engine.vectors;

import java.awt.Shape;
import java.util.Map;

import javax.xml.namespace.QName;

import ui.engine.Point;

public interface Vector extends Cloneable {

	String getType();
	String getStyleString();
	
	Object clone();

	Shape getShape();
	
	Map<String, String> getStyle();
	
	void move(Point offset);
	void transform(double offset);
	void normalize(long screenSize, int screenWidth, int minSize);
	void setStyle(Map<String, String> s);
	void assingParamerters(Map<QName, Object> p);
	
	default long convert(double value, double fromRefrence, double toRefrence) {
		return Math.round(((value/fromRefrence)*toRefrence));
	}

}
