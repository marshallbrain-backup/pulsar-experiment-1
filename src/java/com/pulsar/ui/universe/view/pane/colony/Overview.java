package ui.universe.view.pane.colony;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import input.Keyboard;
import input.Mouse;
import species.colony.Colony;
import ui.universe.view.button.Button;
import ui.universe.view.pane.BuildingUI;
import ui.universe.view.pane.DistrictUI;
import ui.universe.view.pane.Pane;
import ui.universe.view.pane.Resorce;
import ui.universe.view.pane.detail.BuildingDet;
import ui.universe.view.pane.detail.DistrictDet;
import ui.universe.view.pane.detail.ProjectDet;

public class Overview extends Pane {
	
	private int lastDistrictIndex;
	private int lastBuildingIndex;
	
	private ArrayList<String> resorcesDisplay;
	
	private BufferedImage district;
	private BufferedImage building;
	private BufferedImage resource;
	
	private Colony colony;
	private Button projects;
	
	private DistrictUI[] districts;
	private BuildingUI[] buildings;
	
	public Overview(Colony c) {
		
		colony = c;
		
		init();
		
	}
	
	/**
	 * initalize colony overview graphicts
	 */
	public void init() {
		
		lastDistrictIndex = -1;
		lastBuildingIndex = -1;
		
		try {
			
			resource = ImageIO.read(new File("gfx\\ui\\view\\colony\\resorces.png"));
			district = ImageIO.read(new File("gfx\\ui\\view\\colony\\district\\area.png"));
			building = ImageIO.read(new File("gfx\\ui\\view\\colony\\building\\area.png"));

			projects = new Button(ImageIO.read(new File("gfx\\ui\\view\\button2.png")), "Projects", 790, 53);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		resorcesDisplay = new ArrayList<String>();
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(new File("interface\\colony_resorces.txt")));
			
			for(String l = br.readLine(); l != null; l = br.readLine()) {
				resorcesDisplay.add(l);
			}
			
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public boolean action(Mouse m, Keyboard k) {
		
//		if(!m.buttonDownOnce(1))
//			return false;

		int x = (int)Math.round(m.getPosition().getX()-this.x);
		int y = (int)Math.round(m.getPosition().getY()-this.y);
		
		if(projects.action(m, k)) {
			detail.setPane(new ProjectDet(detail, colony.getBody()), "Tool District");
		}
		
		int x1 = 0;
		int y1 = 0;
		
		int xi = 0;
		int yi = 0;
		
		x1 = x-25+80;
		y1 = y-260+100;
		
		xi = x1/80;
		yi = y1/100;
		
		if(0 < xi && xi <= 4) {
			if(0 < yi && yi <= 2) {
				xi--;
				yi--;
				int i = xi+yi*4;
				if(districts[i].action(m, k, i)) {
					
					if(i > lastDistrictIndex+1) {
						i = lastDistrictIndex+1;
					}
					
					if(districts[i].getDistrict().getType() == null && districts[i].getDistrict().getPendingType() == null) {
						detail.setPane(new DistrictDet(districts, i, detail), "Tool District");
					} else {
						detail.setPane(new DistrictDet(districts[i].getDistrict(), detail), "District Details");
					}
					
					return true;
					
				}
			}
		}
		
		x1 = x-10+50;
		y1 = y-490+50;
		
		xi = x1/55;
		yi = y1/55;
		
		if(0 < xi && xi <= 11) {
			if(0 < yi && yi <= 4) {
				xi--;
				yi--;
				int i = xi+yi*11;
				if(buildings[i].action(m, k, i)) {
					
					if(i > lastBuildingIndex+1) {
						i = lastBuildingIndex+1;
					}
					
					if(buildings[i].getBuilding().getType() == null) {
						detail.setPane(new BuildingDet(buildings, i, detail), "Build Building");
					} else {
						detail.setPane(new BuildingDet(buildings[i].getBuilding(), detail), "Building Details");
					}
					
					return true;
					
				}
			}
		}

		if(m.buttonDownOnce(1))
			detail.setPane(null);
		
//		x -= (i%4)*80;
//		y -= (i/4)*100;
		
//		for(int i = 0; i < districts.length; i++) {
//			districts[i].clicked(x-25, y-260, i, detail);
//		}
//		
//		for(int i = 0; i < buildings.length; i++) {
//			buildings[i].clicked(x-25, y-510, i, detail);
//		}
		
		return true;
		
	}

	public void render(Graphics g, int x1, int y1) {
		super.render(g, x1, y1);
		
		Graphics2D g2d = (Graphics2D)g;
		
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 22);
		
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		FontMetrics metrics = g.getFontMetrics(font);
		
		//sets offset from cornor of view
		x += 12;
		y += 12;
		
		g.drawImage(district, x, y+210, null);
		g2d.drawString("Districts", x+6, y+235);
		
		districts = colony.getDistricts();
		
		//draws all the discricts using districtUI
		for(int i = 0; i < districts.length; i++) {
			if(districts[i].render(g, i, x+15, y+250)) {
				lastDistrictIndex = i;
			}
		}
		
		g.drawImage(building, x, y+460, null);
		g2d.drawString("Buildings", x+6, y+485);
		
		buildings = colony.getBuildings();
		
		for(int i = 0; i < buildings.length; i++) {
			if(buildings[i].render(g, i, x+15, y+500)) {
				lastBuildingIndex = i;
			}
		}

		g2d.setFont(font);
		
		g.drawImage(resource, x+350, y+210, null);
		g2d.drawString("Resorces Production", x+350+resource.getWidth()-metrics.stringWidth("Resorces Production")-12, y+235);
		
		renderResorces(g, x+350, y+210, x+350+resource.getWidth(), colony.getResourceManager().getResourceIncome());
		colony.getResourceManager().getBuildQueue().render(g, x+290+350, y+95+210);
		
		projects.render(g);
		
	}
	
	private void renderResorces(Graphics g, int x, int y, int maxX, HashMap<String, Double> renorceIncome) {
		
		int startX = x;
		
		for(String r: resorcesDisplay) {
			
			if(!renorceIncome.containsKey(r))
				continue;
			
			int newX = Resorce.render(g, x, y, maxX, r, renorceIncome.get(r));
			if (newX < x) {
				x = startX;
				y += 20;
			} else {
				x = newX;
			}
			
		}
		
	}

}
