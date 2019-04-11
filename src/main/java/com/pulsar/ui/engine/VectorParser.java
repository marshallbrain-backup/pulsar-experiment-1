package ui.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import math.Other;

public class VectorParser {

	public static VectorLayer getVectors(String fileName) {
		
		try {
			return readVectorFile(fileName);
		} catch (FileNotFoundException | JAXBException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	private static VectorLayer readVectorFile(String fileName) throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(VectorLayer.class, Circle.class);
		Unmarshaller um = context.createUnmarshaller();
		VectorLayer vectors = (VectorLayer) um.unmarshal(new FileReader(new File(fileName)));
		
		for(Vector v: vectors.getVectors()) {
			v.setStyle(Other.convertStyle(v.getStyleString()));
		}
		
		return vectors;
	}

}
