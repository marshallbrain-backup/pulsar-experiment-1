package ui.engine.actions;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

@XmlRootElement(name = "action")
public class ActionGroup {

	@XmlAnyElement(lax = true)
	private List<Action> actions;
	
	@XmlAnyAttribute
	private Map<QName, Object> parameters;
	
	public List<Action> getActions(){
		return actions;
	}
	
	public void propegateParameters() {
		for(Action a: actions) {
			a.assingParamerters(parameters);
		}
	}
	
	public void assingParameters(Object... p) {
		Object[] s = parameters.keySet().toArray();
		for(int i = 0; i < p.length; i++) {
			parameters.put((QName) s[i], p[i]);
		}
	}
	
}
