package ui.engine.vectors;


public class Segment {
	
	private String type;
	private double x;
	private double y;
	
	public Segment() {
		
		type = "";
		x = 0;
		y = 0;
		
	}
	
	public Segment(String t, String n1, String n2) {
		
		type = t;
		
		try {
			x = Double.parseDouble(n1);
		} catch(NumberFormatException e){
			x = 0;
		}
		try {
			y = Double.parseDouble(n2);
		} catch(NumberFormatException e){
			y = 0;
		}
		
	}

	public Segment(Segment s, Segment last) {
		
		type = s.type;
		x = 0;
		y = 0;
		
		if(type.equalsIgnoreCase("z")) {
			return;
		}
		
		if(type.equalsIgnoreCase("v")) {
			type = "l";
			x = s.x;
			if(type.equals("V")) {
				y = last.y;
			}
		} else if(type.equalsIgnoreCase("h")) {
			type = "l";
			y = s.x;
			if(type.equals("H")) {
				x = last.x;
			}
		} else {
			x = s.x;
			y = s.y;
		}
		
		if(type.toLowerCase().equals(type)) {
			type = type.toUpperCase();
			x += last.x;
			y += last.y;
		}
		
	}

	public void setX(double i) {
		x = i;
	}
	
	public double getX() {
		return x;
	}
	
	public void setY(double i) {
		y = i;
	}
	
	public double getY() {
		return y;
	}
	
	public void setType(String i) {
		type = i;
	}
	
	public String getType() {
		return type;
	}
	
	public String toString() {
		return type + " " + x + "," + y;
	}
	
}
