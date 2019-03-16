package ui.universe.view.pane.detail;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import bodys.Body;
import files.type.Type;
import files.type.TypePlanetaryProjects;
import input.Keyboard;
import input.Mouse;
import ui.universe.view.pane.Pane;
import ui.universe.view.pane.list.ListProjectType;

public class ProjectDet extends Pane {
	
	private ListProjectType projects;
	private Detail parent;
	private Body body;

	public ProjectDet(Detail d, Body b) {
		
		parent = d;
		body = b;
		
		projects = new ListProjectType(parent.getResourceManager());
		
		ArrayList<Type> p = new ArrayList<Type>(parent.getResourceManager().getProjectTypes());
		
		projects.set(p);
		
	}

	public boolean action(Mouse mo, Keyboard k) {
		
		if(!(mo.buttonClicked(1)))
			return false;
		
		Mouse m = new Mouse(mo, x, y);

		int x = (int)Math.round(m.getPosition().getX());
		int y = (int)Math.round(m.getPosition().getY());
		
		TypePlanetaryProjects t = projects.clicked(x-12, y-57);
		if(t != null) {
			//TODO get build requects to planet
			parent.getResourceManager().getSpecies().createStation(body);
			return true;
		}
		
		return false;
		
	}

	public void render(Graphics g, int x1, int y1) {
		super.render(g, x1, y1);
		
		Graphics2D g2d = (Graphics2D)g;
		
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
		
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
//		FontMetrics metrics = g.getFontMetrics(font);
		
		parent.setName("Planetary Projects");
		projects.render(g, x+12, y+57);
		
	}

}
