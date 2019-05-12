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
		
		this(s);
		
		if(type.equalsIgnoreCase("z")) {
			return;
		}
		
		if(type.equals("V")) {
			type = "L";
			x = last.x;
		} else if(type.equals("H")) {
			type = "L";
			y = last.y;
		}
		
		if(type.toLowerCase().equals(type)) {
			type = type.toUpperCase();
			x += last.x;
			y += last.y;
		}
		
	}

	public Segment(Segment s) {
		
		type = s.type;
		x = 0;
		y = 0;
		
		if(type.equalsIgnoreCase("z")) {
			return;
		}
		
		if(type.equalsIgnoreCase("v")) {
			if(type.equals(type.toLowerCase())) {
				type = "l";
			}
			y = s.x;
		} else if(type.equalsIgnoreCase("h")) {
			if(type.equals(type.toLowerCase())) {
				type = "l";
			}
			x = s.x;
		} else {
			x = s.x;
			y = s.y;
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
