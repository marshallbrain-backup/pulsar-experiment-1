package ui.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VectorParser {

	public static List<Vector> getVectors(String fileName) {
		
		List<Vector> vectorList = new ArrayList<Vector>();
		List<Map<String, String>> properties = readVectorFile(fileName);
		for(Map<String, String> m: properties) {
			switch(m.get("type")) {
			case "circle":
				m.remove("type");
				vectorList.add(new Circle(m));
				break;
			default:
				System.out.println("unreconised vector type");
			}
		}
		
		return vectorList;
		
	}
	
	private static List<Map<String, String>> readVectorFile(String fileName) {
		
		List<Map<String, String>> vectorList = new ArrayList<Map<String, String>>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			read(br, vectorList);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return vectorList;
		
	}
	
	private static void read(BufferedReader br, List<Map<String, String>> vectorList) throws IOException {
		
		String line = null;
		while ((line = br.readLine()) != null) {
			
			line = line.replaceAll("\\s+","");
			if(line.startsWith("<")) {
				Map<String, String> vector = new HashMap<String, String>();
				vector.put("type", line.replace("<", ""));
				readPropertys(br, vector);
				vectorList.add(vector);
			}
			
		}
		
	}
	
	private static void readPropertys(BufferedReader br, Map<String, String> vector) throws IOException {
		
		String line = null;
		while ((line = br.readLine()) != null) {

			line = line.replaceAll("\\s+","");
			if(line.equals("/>"))
				return;
			
			String[] l = line.split("=");
			vector.put(l[0], l[1]);
			
		}
		
	}

}
