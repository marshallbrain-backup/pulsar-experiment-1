package math;

public class Unit {
	
	private long number;
	private long exponent;
	
	public Unit(String n) {
		
		long e = Integer.parseInt(n.split("e")[1]);
		String i = n.split("e")[0];
		
		long d = i.indexOf(".");
		e += i.length()-d-1;
		
		number = Integer.parseInt(i.replace(".", ""));
		exponent = e;
		
	}

	public long getRawNum() {
		return number;
	}

	public long getRawNum(Unit m) {
		if(m.number > number)
			return number*(long) Math.pow(10, m.exponent-exponent);
		return number;
	}
	
}
