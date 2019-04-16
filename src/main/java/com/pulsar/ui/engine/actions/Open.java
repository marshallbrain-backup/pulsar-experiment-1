package ui.engine.actions;

import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;

import ui.engine.actions.Effect;

@XmlRootElement(name = "open")
public class Open implements Effect {
	
	@XmlAttribute(name = "give")
	private String par;
	@XmlValue
	private String location;
	
	private Map<QName, Object> parameters;
	
	@Override
	public String getEffect() {
		return location + ";;" + par;
	}
	
	public String getType() {
		return "open";
	}

	@Override
	public Object[] getParamerters() {
		return parameters.values().toArray();
	}

	@Override
	public void assingParamerters(Map<QName, Object> p) {
		parameters = p;
	}
	
}
