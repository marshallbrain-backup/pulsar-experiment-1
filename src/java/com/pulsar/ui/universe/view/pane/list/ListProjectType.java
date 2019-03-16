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
import files.type.TypePlanetaryProjects;
import species.colony.ResourceManager;

public class ListProjectType {
	
	private ArrayList<TypePlanetaryProjects> projects;
	
	private BufferedImage info;
	private BufferedImage border;
	
	private ResourceManager resource;
	
	public ListProjectType(ResourceManager r) {
		
		projects = new ArrayList<TypePlanetaryProjects>();
		
		try {
			info = ImageIO.read(new File("gfx\\ui\\view\\colony\\building\\info.png"));
			border = ImageIO.read(new File("gfx\\ui\\view\\colony\\building\\boarder.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		resource = r;
		
	}

	public void set(ArrayList<Type> projcetTypes) {
		
		projects.clear();
		
		for(Type t: projcetTypes) {
			TypePlanetaryProjects d = (TypePlanetaryProjects) t;
			projects.add(d);
		}
	}
	
	public TypePlanetaryProjects clicked(int x, int y) {
		
		for(int i = 0; i < projects.size(); i++) {
			if(0 < x && x < info.getWidth()) {
				if(info.getHeight()*i < y && y < info.getHeight()*(i+1)) {
					return projects.get(i);
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
		
		for(int i = 0; i < projects.size(); i++) {
			if(projects.get(i) != null) {
				
				g.drawImage(info, x, y, null);
				g.drawImage(border, x+5, y+25, null);
				g.drawImage(projects.get(i).getIcon(), x+6, y+26, null);
				
				g2d.drawString(projects.get(i).getName(), x+5, y+15);
				
				String dis = projects.get(i).getName() + "_description";
				int y1 = y+20;
				for (String line : dis.split("\n"))
					g2d.drawString(line, x+60, y1 += metrics.getHeight());
				
				y += (info.getHeight()+5);
				
			}
		}
		
		return true;
		
	}

}
