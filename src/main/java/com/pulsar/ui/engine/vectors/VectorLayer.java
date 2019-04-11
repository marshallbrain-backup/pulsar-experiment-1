package ui.engine.vectors;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "vector_layer")
public class VectorLayer {

	@XmlAnyElement(lax = true)
	private List<Vector> vectors;
	
	public List<Vector> getVectors(){
		return vectors;
	}

}
