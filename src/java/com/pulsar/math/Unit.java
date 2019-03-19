package math;

public class Unit {
	
	private int number;
	private int exponent;
	
	public Unit(String n) {
		
		int e = Integer.parseInt(n.split("e")[1]);
		String i = n.split("e")[0];
		
		int d = i.indexOf(".");
		e += i.length()-d-1;
		
		number = Integer.parseInt(i.replace(".", ""));
		exponent = e;
		
	}
	
}
