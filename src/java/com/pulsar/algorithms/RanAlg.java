package algorithms;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class RanAlg {
	
	private static Random r = new Random();
	
	public static int randomInt(int min, int max) {
		return (int) Math.floor(randomDouble(min, max, 0));
	}

	public static double randomDouble(double min, double max, int d) {
		return round((r.nextDouble()*(max-min) + min), d);
	}
	
	public static double round(double value, int places) {
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}
