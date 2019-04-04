package ui.engine;

import java.awt.Color;

import bodys.Body;

public interface Vector {

	String getType();
	
	Color getFillColor();
	
	Vector copy(Body b);
	
	void move(Point offsetAmount, long screenSize, int screenWidth);
	void normalize(long screenSize, int screenWidth, int minSize);

}
