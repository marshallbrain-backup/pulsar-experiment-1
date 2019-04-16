package ui.engine.actions;

import java.util.Map;

import javax.xml.namespace.QName;

public interface Effect {
	
	String getType();

	Object[] getEffect();
	Object[] getParamerters();
	
	void assingParamerters(Map<QName, Object> p);
	
}
