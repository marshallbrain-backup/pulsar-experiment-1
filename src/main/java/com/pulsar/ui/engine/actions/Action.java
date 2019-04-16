package ui.engine.actions;

import java.awt.geom.Area;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import input.Keyboard;
import input.Mouse;

public interface Action {
	
	List<Effect> getEffect();
	
	boolean didAction(Mouse m, Keyboard k, Area area);
	
	Object[] getParamerters();
	
	void assingParamerters(Map<QName, Object> p);
	
}
