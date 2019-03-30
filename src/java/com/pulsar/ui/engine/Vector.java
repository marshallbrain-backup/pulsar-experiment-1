package ui.engine;

import java.awt.Color;

public interface Vector {

	void setSize(long s);
	void setTempSize(long z, int max, int min);

	String getType();

	Color getFillColor();

}
