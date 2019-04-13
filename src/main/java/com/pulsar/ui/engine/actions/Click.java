package ui.engine.actions;

import java.awt.geom.Area;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import input.Keyboard;
import input.Mouse;
import ui.engine.Point;
import ui.engine.actions.Action;

@XmlRootElement(name = "click")
public class Click implements Action {

	@XmlAnyElement(lax = true)
	private List<Effect> effect;

	@Override
	public List<Effect> getEffect(){
		return effect;
	}

	@Override
	public boolean didAction(Mouse m, Keyboard k, Area area) {
		if(!effect.isEmpty() && m.buttonClicked(1)) {
			Point mp = new Point(m.getPosition());
			if(area.contains(mp.getX(), mp.getY())) {
				return true;
			}
		}
		return false;
	}
	
}
