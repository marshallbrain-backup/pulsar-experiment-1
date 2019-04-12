package ui.engine.actions;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import ui.engine.vectors.Vector;

@XmlRootElement(name = "action")
public class ActionGroup {

	@XmlAnyElement(lax = true)
	private List<Action> vectors;
	
	public List<Action> getActions(){
		return vectors;
	}
	
}
