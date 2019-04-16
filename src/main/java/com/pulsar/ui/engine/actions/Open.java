package ui.engine.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
	public Object[] getEffect() {
		
		location = location.replaceAll("\\s+","");
		par = par.replaceAll("\\s+","");
		List<Object> e = new ArrayList<Object>();
		
		e.add(location);
		
		if(par.contains("@")) {
			for(int i = par.indexOf("@", 0); i != -1; i = par.indexOf("@", 0)) {
				String v = par.substring(i+1, par.indexOf(";", i));
				e.add(parameters.get(new QName(v)));
				par = par.replace("@"+v+";", "");
			}
		}
		
		e.addAll(Arrays.asList(par.split(";")));
		
		return e.toArray();
		
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
