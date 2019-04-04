package ui.engine;

import java.awt.Color;

public interface Vector {

	String getType();

	Color getFillColor();
	
	Vector copy();

	void transform(long distance, double angle, long radius, long screenSize, int screenWidth, int minSize);
	void transform(Point offsetAmount, long screenSize, int screenWidth);

}
