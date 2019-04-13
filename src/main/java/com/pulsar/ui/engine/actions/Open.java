package ui.engine.actions;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import ui.engine.actions.Effect;

@XmlRootElement(name = "open")
public class Open implements Effect {

	public static final String TYPE = "open";
	@XmlAttribute
	private String tab;
	@XmlValue
	private String location;
	
	@Override
	public String getEffect() {
		return location + ";" + tab;
	}
	
	public String getType() {
		return TYPE;
	}
	
}
