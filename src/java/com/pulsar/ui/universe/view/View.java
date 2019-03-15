package ui.universe.view;

import java.awt.Graphics;

import input.Keyboard;
import input.Mouse;

//TODO convert all buffered images to be static for faster loading
public class View {
	
	protected int x;
	protected int y;
	
	protected boolean close = false;

	public void render(Graphics g) {
	}

	public boolean action(Mouse m, Keyboard k) {
		return false;
	}
	
	public boolean toClose() {
		return close;
	}

}
