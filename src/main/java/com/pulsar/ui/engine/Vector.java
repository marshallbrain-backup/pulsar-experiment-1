package ui.engine;

import java.awt.Shape;
import java.util.Map;

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
