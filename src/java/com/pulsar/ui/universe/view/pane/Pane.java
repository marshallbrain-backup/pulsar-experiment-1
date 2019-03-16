package ui.universe.view.pane;

import java.awt.Graphics;

import input.Keyboard;
import input.Mouse;
import ui.universe.view.pane.detail.Detail;

public class Pane {
	
	protected int x;
	protected int y;
	
	protected Detail detail;

	public void render(Graphics g, int x1, int y1) {
		x = x1;
		y = y1;
	}

	public boolean action(Mouse m, Keyboard k) {
		return false;
	}

	public void add(Detail d) {
		detail = d;
	}

	public boolean shouldReload() {
		return false;
	}

}
