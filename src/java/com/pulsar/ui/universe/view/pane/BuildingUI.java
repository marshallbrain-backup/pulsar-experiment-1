package ui.universe.view.pane;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import input.Keyboard;
import input.Mouse;
import species.colony.build.Building;
import ui.universe.Tooltip;

public class BuildingUI {
	
	private int x;
	private int y;
	private int tooltipX;
	private int tooltipY;
	
	private boolean renderTooltip;

	private BufferedImage border;
	private BufferedImage empty;
	
	private Building building;

	public BuildingUI(Building b) {
		
		renderTooltip = false;
		
		building = b;
		
		try {
			border = ImageIO.read(new File("gfx\\ui\\view\\colony\\building\\boarder.png"));
			empty = ImageIO.read(new File("gfx\\ui\\view\\colony\\building\\building_empty.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean action(Mouse m, Keyboard k, int i) {

		int mx = (int)Math.round(m.getPosition().getX()-this.x);
		int my = (int)Math.round(m.getPosition().getY()-this.y);
		
		int dx = (i%11)*55;
		int dy = (i/11)*55;
		
		if(0 < mx-dx && mx-dx < border.getWidth()) {
			if(0 < my-dy && my-dy < border.getHeight()) {
				renderTooltip = true;
				tooltipX = mx;
				tooltipY = my;
				if(m.buttonDownOnce(1))
					return true;
			}
		}
		
		return false;
		
	}
	
	public boolean render(Graphics g, int i, int x1, int y1) {
		
		x = x1;
		y = y1;
		
		int dx = (i%11)*55;
		int dy = (i/11)*55;
		
		g.drawImage(border, x+dx, y+dy, null);
		
		if(building.getType() != null) {
			g.drawImage(building.getType().getIcon(), x+dx+1, y+dy+1, null);
			return true;
		} else {
			g.drawImage(empty, x+dx+1, y+dy+1, null);
		}
		
		if(renderTooltip) {
			Tooltip.set((Graphics2D)g, building.getTooltip(), x+tooltipX, y+tooltipY);
			renderTooltip = false;
		}
		
		return false;
		
	}

	public Building getBuilding() {
		return building;
	}

}
