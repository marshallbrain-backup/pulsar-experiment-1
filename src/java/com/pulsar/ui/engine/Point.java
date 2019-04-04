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

	public Point(Point p, long fromRefrence, long toRefrence) {
		this(convert(p.getXLong(), fromRefrence, toRefrence), convert(p.getYLong(), fromRefrence, toRefrence));
	}

	public int getXInt() {
		return Math.toIntExact(getXLong());
	}

	public int getYInt() {
		return Math.toIntExact(getYLong());
	}

	public long getXLong() {
		return Math.round(x);
	}

	public long getYLong() {
		return Math.round(y);
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
		this.x = p.x;
		this.y = p.y;
	}

	public void move(double dx, double dy) {
		this.x = x + dx;
		this.y = y + dy;
	}

	public void move(double dx, double dy, long fromRefrence, long toRefrence) {
		move(convert(dx, fromRefrence, toRefrence), convert(dy, fromRefrence, toRefrence));
	}
	
	private static long convert(double value, double fromRefrence, double toRefrence) {
		return Math.round(((value/fromRefrence)*toRefrence));
	}

}
