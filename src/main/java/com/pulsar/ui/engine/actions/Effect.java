package ui.engine.actions;

import java.util.Map;

import javax.xml.namespace.QName;

public interface Effect {
	
	public String getEffect();
	public String getType();
	
	Object[] getParamerters();
	
	void assingParamerters(Map<QName, Object> p);
	
}
