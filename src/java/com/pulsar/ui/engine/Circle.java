package ui.engine;

public class Circle implements Vector {
	
	private int centerX;
	private int centerY;
	private int radius;
	
	public Circle(int cx, int cy, int r) {
		centerX = cx;
		centerY = cy;
		radius = r;
	}
	
	public int getCenterX() {
		return centerX;
	}
	
	public int getCenterY() {
		return centerY;
	}
	
	public int getRadius() {
		return radius;
	}
	
}
