package math;

import java.io.File;

public class Other {

	public static String getExtension(File file) {
		String name = file.getName();
		int i = name.lastIndexOf('.');
		return name.substring(i+1);
	}

}
