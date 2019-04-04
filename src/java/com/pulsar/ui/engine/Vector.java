package ui.engine;

import java.awt.Color;
import java.awt.Shape;

public interface Vector {

	String getType();
	
	Color getFillColor();
	
	Vector copy();

	Shape getShape();
	
	void move(Point offset);
	void transform(double offset);
	void normalize(long screenSize, int screenWidth, int minSize);

}
