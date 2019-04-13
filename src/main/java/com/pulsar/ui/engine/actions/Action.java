package ui.engine.actions;

import java.util.List;

import input.Keyboard;
import input.Mouse;

public interface Action {
	
	public List<Effect> getEffect();
	
	public boolean didAction(Mouse m, Keyboard k);
	
}
