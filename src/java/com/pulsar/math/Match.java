package math;

import java.util.regex.Pattern;

public class Match {
	
	private static String doublePattern = "\\p{Digit}\\.\\p{Digit}";
	private static String intPattern = "\\p{Digit}";
	private static String hexPattern = "\\p{XDigit}";
	
	public static boolean isDouble(String s) {
		return (Pattern.matches(doublePattern, s) || isInt(s));
	}
	
	public static boolean isInt(String s) {
		return isInt(s, 10);
	}

	public static boolean isInt(String s, int i) {
		switch(i) {
		case 10:
			return Pattern.matches(intPattern, s);
		case 16:
			return Pattern.matches(hexPattern, s);
		default:
			return false;
		}
	}

}
