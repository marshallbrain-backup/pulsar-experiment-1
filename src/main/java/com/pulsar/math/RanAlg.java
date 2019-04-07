package math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class RanAlg {
	
	private static Random r = new Random();
	
	public static int randomInt(int min, int max) {
		return (int) Math.floor(randomDouble(min, max));
	}

	public static double randomDouble(double min, double max) {
		return (r.nextDouble()*(max-min) + min);
	}

	public static double randomDouble(double min, double max, int d) {
		long a = Math.round(randomDouble(min, max)*Math.pow(10, d));
		double b = new BigDecimal(a).movePointLeft(d).doubleValue();
		return b;
	}

	public static double randomDouble(double v, double min, double max, int d) {
		return new BigDecimal((randomDouble(min, max, d)+v)*Math.pow(10, d)).movePointLeft(d).doubleValue();
	}
	
	public static Unit randomUnit(Unit min, Unit max) {
		double d = (r.nextDouble()*(max.doubleValue()-min.doubleValue()) + min.doubleValue());
		return new Unit(Math.round(d));
	}

}
