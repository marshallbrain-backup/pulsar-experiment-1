package ui.engine.actions;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import input.Keyboard;
import input.Mouse;
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
	public boolean didAction(Mouse m, Keyboard k) {
		return false;
	}
	
}
