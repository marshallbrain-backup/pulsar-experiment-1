package ui.universe.view.pane.detail;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import files.type.Type;
import files.type.TypeBuilding;
import input.Keyboard;
import input.Mouse;
import species.colony.build.Building;
import ui.universe.view.button.Button;
import ui.universe.view.pane.BuildingUI;
import ui.universe.view.pane.Pane;
import ui.universe.view.pane.list.ListBuildingType;

public class BuildingDet extends Pane {
	
	private int index;
	
	private State active;
	
	private ListBuildingType current;
	private ListBuildingType buildList;
	private ListBuildingType replaceList;
	
	private Building building;
	private BuildingUI[] buildings;
	private Detail parent;
	
	private Button replace;
	private Button remove;
	private Button downgrade;
	private Button disable;
	private Button placeholder;
	
	private enum State {
		DEFALT,
		REPLACE
	}

	public BuildingDet(Building b, Detail de) {
		
		building = b;
		parent = de;
		
		index = -1;
		
		active = State.DEFALT;
		
		current = new ListBuildingType(building.getResourceManager());
		replaceList = new ListBuildingType(building.getResourceManager());
		
		ArrayList<Type> t = new ArrayList<Type>(building.getResourceManager().getBuildingTypes());
		t.remove(building.getType());
		
		current.set(building.getType());
		replaceList.set(t);
		
		try {
			replace = new Button(ImageIO.read(new File("gfx\\ui\\view\\button2.png")), "Replace", 12, 57);
			remove = new Button(ImageIO.read(new File("gfx\\ui\\view\\button2.png")), "Remove", 147, 57);
			downgrade = new Button(ImageIO.read(new File("gfx\\ui\\view\\button2.png")), "Downgrade", 147, 57);
			disable = new Button(ImageIO.read(new File("gfx\\ui\\view\\button2.png")), "Disable", 12, 57+45);
			placeholder = new Button(ImageIO.read(new File("gfx\\ui\\view\\button2.png")), "???", 147, 57+45);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		placeholder.setActive(false);
		
	}

	public BuildingDet(BuildingUI[] b, int i, Detail de) {
		
		parent = de;
		buildings = b;
		index = i;
		
		buildList = new ListBuildingType(buildings[0].getBuilding().getResourceManager());
		
		ArrayList<Type> t = new ArrayList<Type>(buildings[0].getBuilding().getResourceManager().getBuildingTypes());
		buildList.set(t);
		
	}

	public boolean action(Mouse m, Keyboard k) {
		
		if(!m.buttonDownOnce(1))
			return false;

		int x = (int)Math.round(m.getPosition().getX()-this.x);
		int y = (int)Math.round(m.getPosition().getY()-this.y);
		
		if(building == null) {
			TypeBuilding t = buildList.clicked(x-12, y-57);
			if(t != null) {
				buildings[index].getBuilding().build(t);
				index++;
				if(index >= buildings.length)
					parent.close();
				return true;
			}
		}
		
//		if(build.clicked(x1, y1)) {
//			district.build();
//			active = State.DEFALT;
//			return true;
//		} else if(remove.clicked(x1, y1)) {
//			district.remove();
//			active = State.DEFALT;
//			return true;
//		} else if(replace.clicked(x1, y1)) {
//			active = State.REPLACE;
//			return true;
//		} else if(retool.clicked(x1, y1)) {
//			active = State.RETOOL;
//			return true;
//		}
		
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
		
		if(building == null) {
			parent.setName("Build Building");
			buildList.render(g, x+12, y+57);
		} else {
			replace.render(g, x, y);
			remove.render(g, x, y);
			disable.render(g, x, y);
			placeholder.render(g, x, y);
		}
		
	}

}
