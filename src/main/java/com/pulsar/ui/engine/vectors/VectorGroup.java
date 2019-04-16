package ui.engine.vectors;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

@XmlRootElement(name = "vector_layer")
public class VectorGroup {

	@XmlAnyElement(lax = true)
	private List<Vector> vectors;
	
	@XmlAnyAttribute
	private Map<QName, Object> parameters;
	
	public List<Vector> getVectors(){
		return vectors;
	}
	
	public void propegateParameters() {
		for(Vector a: vectors) {
			a.assingParamerters(parameters);
		}
	}
	
	public void assingParameters(Object... p) {
		for(Entry<QName, Object> e: parameters.entrySet()) {
			parameters.put(e.getKey(), p[Integer.parseInt(e.getValue().toString())+1]);
		}
	}

}
