package ui.engine.vectors;

import java.awt.Shape;
import java.util.Map;

import ui.engine.Point;

public interface Vector {

	String getType();
	String getStyleString();
	
	Object clone();

	Shape getShape();
	
	Map<String, String> getStyle();
	
	void move(Point offset);
	void transform(double offset);
	void normalize(long screenSize, int screenWidth, int minSize);
	void setStyle(Map<String, String> s);

}
