package ui.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XmlParser {

	public static Object getXml(String fileName, Class<?>[] classList) {
		
		try {
			return readXmlFile(fileName, classList);
		} catch (FileNotFoundException | JAXBException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	private static Object readXmlFile(String fileName, Class<?>[] classList) throws JAXBException, FileNotFoundException {
		JAXBContext context = JAXBContext.newInstance(classList);
		Unmarshaller um = context.createUnmarshaller();

		FileReader reader = new FileReader(new File(fileName));
		
		try {
			if(!reader.ready()) {
				reader.close();
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Object vectors = um.unmarshal(reader);
		
		return vectors;
	}

}
