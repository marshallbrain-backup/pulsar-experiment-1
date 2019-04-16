package ui.engine.actions;

import java.awt.geom.Area;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

import input.Keyboard;
import input.Mouse;
import ui.engine.Point;
import ui.engine.actions.Action;

@XmlRootElement(name = "click")
public class Click implements Action {

	@XmlAnyElement(lax = true)
	private List<Effect> effects;
	
	private Map<QName, Object> parameters;

	@Override
	public List<Effect> getEffect(){
		return effects;
	}

	@Override
	public boolean didAction(Mouse m, Keyboard k, Area area) {
		if(!effects.isEmpty() && m.buttonClicked(1)) {
			Point mp = new Point(m.getPosition());
			if(area.contains(mp.getX(), mp.getY())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Object[] getParamerters() {
		return parameters.values().toArray();
	}

	@Override
	public void assingParamerters(Map<QName, Object> p) {
		parameters = p;
		for(Effect e: effects) {
			e.assingParamerters(parameters);
		}
	}
	
}
