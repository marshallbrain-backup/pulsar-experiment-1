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
import files.type.TypeDistrict;
import input.Keyboard;
import input.Mouse;
import species.colony.build.District;
import ui.universe.view.button.Button;
import ui.universe.view.pane.DistrictUI;
import ui.universe.view.pane.Pane;
import ui.universe.view.pane.list.ListDistrictType;
import ui.universe.view.pane.list.ListDistricts;

public class DistrictDet extends Pane {
	
	private int index;
	
	private State active;
	
	private ListDistrictType current;
	private ListDistrictType retoolList;
	private ListDistrictType toolList;
	private ListDistricts replaceList;
	
	private District district;
	private DistrictUI[] districts;
	private Detail parent;
	
	private Button build;
	private Button remove;
	private Button replace;
	private Button retool;
	
	private enum State {
		DEFALT,
		REPLACE,
		RETOOL
	}

	public DistrictDet(District d, Detail de) {
		
		district = d;
		parent = de;
		
		active = State.DEFALT;
		
		current = new ListDistrictType(district.getResourceManager());
		retoolList = new ListDistrictType(district.getResourceManager());
		replaceList = new ListDistricts(district.getResourceManager());
		
		ArrayList<Type> t = new ArrayList<Type>(district.getResourceManager().getDistrictTypes());
		ArrayList<District> p = new ArrayList<District>(district.getResourceManager().getDistricts());
		for(District dis: p) {
			t.remove(dis.getType());
			t.remove(dis.getPendingType());
		}
		p.remove(district);
		
		current.set(d.getType());
		retoolList.set(t);
		replaceList.set(p);
		
		try {
			build = new Button(ImageIO.read(new File("gfx\\ui\\view\\button2.png")), "Build", 12, 57);
			remove = new Button(ImageIO.read(new File("gfx\\ui\\view\\button2.png")), "Remove", 147, 57);
			replace = new Button(ImageIO.read(new File("gfx\\ui\\view\\button2.png")), "Replace", 12, 57+45);
			retool = new Button(ImageIO.read(new File("gfx\\ui\\view\\button2.png")), "Retool", 147, 57+45);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(district.getType() == null) {
			build.setActive(false);
			remove.setActive(false);
			replace.setActive(false);
		}
		
	}

	public DistrictDet(DistrictUI[] b, int i, Detail de) {
		
		parent = de;
		districts = b;
		index = i;
		
		toolList = new ListDistrictType(districts[0].getDistrict().getResourceManager());
		
		ArrayList<Type> t = new ArrayList<Type>(districts[0].getDistrict().getResourceManager().getDistrictTypes());
		ArrayList<District> p = new ArrayList<District>(districts[0].getDistrict().getResourceManager().getDistricts());
		for(District dis: p) {
			t.remove(dis.getType());
			t.remove(dis.getPendingType());
		}
		
		toolList.set(t);
		
	}

	public boolean action(Mouse mo, Keyboard k) {
		
		if(!(mo.buttonClicked(1)))
			return false;
		
		Mouse m = new Mouse(mo, x, y);

		int x = (int)Math.round(m.getPosition().getX());
		int y = (int)Math.round(m.getPosition().getY());
		
		if(district == null) {
			TypeDistrict t = toolList.clicked(x-12, y-57);
			if(t != null) {
				districts[index].getDistrict().retool(t);
				parent.close();
				return true;
			}
		} else {
			
			if(build.action(m, k)) {
				district.build();
				active = State.DEFALT;
				return true;
			} else if(remove.action(m, k)) {
				district.remove();
				active = State.DEFALT;
				return true;
			} else if(replace.action(m, k)) {
				active = State.REPLACE;
				return true;
			} else if(retool.action(m, k)) {
				active = State.RETOOL;
				return true;
			}
		
			if(active == State.REPLACE) {
				District t = replaceList.clicked(x-12, y-57-45-80);
				if(t != null) {
					district.replace(t);
					return true;
				}
			}
			
			if(active == State.RETOOL) {
				TypeDistrict t = retoolList.clicked(x-12, y-57-45-80);
				if(t != null) {
					district.retool(t);
					parent.close();
					return true;
				}
			}
			
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
		
		if(district == null) {
			parent.setName("Tool District");
			toolList.render(g, x+12, y+57);
		} else {
			
			build.render(g, x, y);
			remove.render(g, x, y);
			replace.render(g, x, y);
			retool.render(g, x, y);
			
			switch (active) {
			case REPLACE:
				g2d.drawString("Replace", x+110, y+57+45+70);
				replaceList.render(g, x+12, y+57+45+80);
				break;
			case RETOOL:
				g2d.drawString("Retool", x+110, y+57+45+70);
				retoolList.render(g, x+12, y+57+45+80);
				break;
			case DEFALT:
				if(current.render(g, x+12, y+57+45+80))
					g2d.drawString("Info", x+122, y+57+45+70);
				break;
			
			}
			
		}
		
	}

}
