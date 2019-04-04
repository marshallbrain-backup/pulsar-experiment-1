package ui.engine;

import java.awt.Color;
import java.awt.Shape;

import bodys.Body;

public interface Vector {

	String getType();
	
	Color getFillColor();
	
	Vector copy(Body b);

	Shape getShape();
	
	void move(Point offsetAmount, long screenSize, int screenWidth);
	void normalize(long screenSize, int screenWidth, int minSize);

}
