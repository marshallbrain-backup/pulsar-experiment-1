package files;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class VariableArray {
	
	private static Map<String, Object> variables;
	
	public static void init() {
		
		variables = new HashMap<String, Object>();
		propegateFolders(new File("gfx\\icons"));
		
	}
	
	private static void propegateFolders(File d) {
		
		if(d.isDirectory()) {
			for(File f: d.listFiles()) {
				propegateFolders(f);
			}
		} else {
			loadFile(d);
		}
		
	}
	
	private static void loadFile(File f) {
		
		BufferedImage i = null;
	    try {
			i = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		variables.put(f.getPath().substring(4, f.getPath().lastIndexOf(".")), i);
		
	}
	
	public static Object getVar(String s) {
		return variables.get(s);
	}
	
}
