package fleets;

import bodys.Body;

public class Action {
	
	private ActionType action;
	
	private Body targetBody;
	
	public Action(ActionType a, Body b) {
		action = a;
		targetBody = b;
	}
	
	public Body getBody() {
		return targetBody;
	}
	
	public ActionType getAction() {
		return action;
	}
	
	public String toString() {
		return action.toString();
	}

}
