package algorithms;

import java.util.regex.Pattern;

public class Match {
	
	private static String douPattern = "([0-9]*)\\.([0-9]*)";
	private static String intPattern = "([0-9]*)";
	
	public static boolean isDouble(String s) {
		return (Pattern.matches(douPattern, s) || Pattern.matches(intPattern, s));
	}
	
	public static boolean isInt(String s) {
		return Pattern.matches(intPattern, s);
	}

}
