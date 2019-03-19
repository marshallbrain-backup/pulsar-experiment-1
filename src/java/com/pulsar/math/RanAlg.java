package math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class RanAlg {
	
	private static Random r = new Random();
	
	public static int randomInt(int min, int max) {
		return (int) Math.floor(randomDouble(min, max, 0));
	}

	public static double randomDouble(double min, double max, int d) {
		long a = Math.round((r.nextDouble()*(max-min) + min)*Math.pow(10, d));
		double b = new BigDecimal(a).movePointLeft(d).doubleValue();
		return b;
	}

	public static double randomDouble(double v, double min, double max, int d) {
		return new BigDecimal((randomDouble(min, max, d)+v)*Math.pow(10, d)).movePointLeft(d).doubleValue();
	}

}
