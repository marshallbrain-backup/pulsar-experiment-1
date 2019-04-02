package ui.engine;

public class Point {
	
	private int x;
	private int y;
	
	public Point() {
		this(0, 0);
	}
	
	public Point(Point p) {
		this(p.x, p.y);
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setLocation(java.awt.Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	public void move(int dx, int dy) {
		this.x = x + dx;
		this.y = y + dy;
	}

}
