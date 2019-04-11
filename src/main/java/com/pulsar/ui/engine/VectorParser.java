package ui.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class VectorParser {

	public static List<VectorLayer> getVectors(String fileName) {
		
		List<VectorLayer> layer = new ArrayList<VectorLayer>();
		
		try {
			layer.add(readVectorFile(fileName));
		} catch (FileNotFoundException | JAXBException e) {
			e.printStackTrace();
		}
		
		return layer;
		
	}
	
	private static VectorLayer readVectorFile(String fileName) throws JAXBException, FileNotFoundException {
		
		JAXBContext context = JAXBContext.newInstance(VectorLayer.class, Circle.class);
		Unmarshaller um = context.createUnmarshaller();
		VectorLayer vectors = (VectorLayer) um.unmarshal(new FileReader(new File(fileName)));
		
		return vectors;
		
	}

}
