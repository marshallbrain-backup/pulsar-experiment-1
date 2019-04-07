package ui.engine;

import input.Keyboard;
import input.Mouse;

public interface UiElement {
	
	boolean action(Mouse m, Keyboard k);
	
	void render(VectorGraphics g);

}
