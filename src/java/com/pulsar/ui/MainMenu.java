package ui;

import java.awt.Graphics;

import settings.Settings;

public class MainMenu {
	
	private String save;

	public MainMenu(Settings settings) {
	}
	
	public boolean tick() {
		save = ";0";
		return true;
	}
	
	public void render(Graphics g) {
		
	}

	public String getSave() {
		return save;
	}

}
