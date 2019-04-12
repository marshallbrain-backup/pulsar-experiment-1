package ui.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import math.Other;
import ui.engine.vectors.Circle;
import ui.engine.vectors.Vector;
import ui.engine.vectors.VectorLayer;

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
		
		System.out.println(vectors.getVectors().get(0) instanceof Vector);
		
		if(!vectors.getVectors().isEmpty() && vectors.getVectors().get(0) instanceof Vector) {
			for(Vector v: vectors.getVectors()) {
				v.setStyle(Other.convertStyle(v.getStyleString()));
			}
		} else {
			vectors = null;
		}
		
		return vectors;
	}

}
