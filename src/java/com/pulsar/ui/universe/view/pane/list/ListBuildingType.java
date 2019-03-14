package ui.universe.view.pane.list;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import files.type.Type;
import files.type.TypeBuilding;
import species.colony.ResourceManager;

public class ListBuildingType {
	
	private ArrayList<TypeBuilding> buildings;
	
	private BufferedImage info;
	private BufferedImage border;
	
	private ResourceManager resource;
	
	public ListBuildingType(ResourceManager r) {
		
		buildings = new ArrayList<TypeBuilding>();
		
		try {
			info = ImageIO.read(new File("gfx\\ui\\view\\colony\\building\\info.png"));
			border = ImageIO.read(new File("gfx\\ui\\view\\colony\\building\\boarder.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		resource = r;
		
	}

	public void set(TypeBuilding d) {
		buildings.clear();
		buildings.add(d);
	}

	public void set(ArrayList<Type> buildingTypes) {
		
		buildings.clear();
		
		for(Type t: buildingTypes) {
			TypeBuilding d = (TypeBuilding) t;
			buildings.add(d);
		}
	}
	
	public TypeBuilding clicked(int x, int y) {
		
		for(int i = 0; i < buildings.size(); i++) {
			if(0 < x && x < info.getWidth()) {
				if(info.getHeight()*i < y && y < info.getHeight()*(i+1)) {
					return buildings.get(i);
				}
			}
		}
		
		return null;
		
	}

	public boolean render(Graphics g, int x, int y) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
		
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		FontMetrics metrics = g.getFontMetrics(font);
		
		for(int i = 0; i < buildings.size(); i++) {
			if(buildings.get(i) != null) {
				
				g.drawImage(info, x, y, null);
				g.drawImage(border, x+5, y+25, null);
				g.drawImage(buildings.get(i).getIcon(), x+6, y+26, null);
				
				g2d.drawString(buildings.get(i).getName(), x+5, y+15);
				
				String dis = buildings.get(i).getName() + "_description";
				int y1 = y+20;
				for (String line : dis.split("\n"))
					g2d.drawString(line, x+60, y1 += metrics.getHeight());
				
				y += (info.getHeight()+5);
				
			}
		}
		
		return true;
		
	}

}
