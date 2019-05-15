package math;

import java.util.regex.Pattern;

public class Match {
	
	private static final Pattern DOUBLE_PATTERN = Pattern.compile("^\\p{Digit}+$" + "\\." + "^\\p{Digit}+$");
	private static final Pattern INT_PATTERN = Pattern.compile("^\\p{Digit}+$");
	private static final Pattern HEX_PATTERN = Pattern.compile("^\\p{XDigit}+$");
	
	public static boolean isDouble(String s) {
		return (DOUBLE_PATTERN.matcher(s).matches() || isInt(s));
	}
	
	public static boolean isInt(String s) {
		return isInt(s, 10);
	}

	public static boolean isInt(String s, int i) {
		switch(i) {
		case 10:
			return INT_PATTERN.matcher(s).matches();
		case 16:
			return HEX_PATTERN.matcher(s).matches();
		default:
			return false;
		}
	}

}
