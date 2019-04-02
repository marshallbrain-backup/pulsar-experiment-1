package ui.engine;

public class Point {
	
	private double x;
	private double y;
	
	public Point() {
		this(0, 0);
	}
	
	public Point(Point p) {
		this(p.x, p.y);
	}
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setLocation(java.awt.Point p) {
		this.x = p.getX();
		this.y = p.getY();
	}

	public void move(double dx, double dy) {
		this.x = x + dx;
		this.y = y + dy;
	}

}
