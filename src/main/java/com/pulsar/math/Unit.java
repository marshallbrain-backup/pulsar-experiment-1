package math;

public class Unit {
	
	private long number;
	private long exponent;
	
	public Unit(String n) {
		
		long e = Long.parseLong(n.split("e")[1]);
		String i = n.split("e")[0];
		
		number = Long.parseLong(i);
		exponent = e;
		
		removeZeros();
		
	}

	public Unit(double i) {
		convert(i, 3);
	}

	public Unit(long i) {
		convert(i, 1);
	}
	
	private void convert(double i, int type) {
		
		long n = 0;
		long e = 0;
		
		switch(type) {
		case 1:
			n = (long) Math.floor(i);
			break;
		}
		
		number = n;
		exponent = e;
		
		removeZeros();
		
	}

	private void removeZeros() {
		String n = String.valueOf(number);
		while(n.endsWith("0")) {
			n = n.substring(0, n.length()-1);
			exponent++;
		}
		number = Long.parseLong(n);
	}
	
	public double doubleValue() {
		return number*Math.pow(10, exponent);
	}
	
}
