package ui.engine;

import java.awt.Color;

public interface Vector {

	String getType();

	Color getFillColor();

	Vector transform(long distance, double angle, long radius, long screenSize, int screenWidth, long minSize);

}
