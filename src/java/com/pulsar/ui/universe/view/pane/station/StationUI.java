package ui.universe.view.pane.station;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import input.Keyboard;
import input.Mouse;
import species.colony.Colony;
import species.colony.Station;
import ui.universe.view.pane.Pane;
import ui.universe.view.pane.detail.Detail;
import ui.universe.view.tab.Tab;
import ui.universe.view.tab.TabLayout;

public class StationUI extends Pane {
	
	private int width = 924;
	private int height = 752;
	
	private TabLayout tabs;
	
	private BufferedImage line;
	private BufferedImage preview;
	private BufferedImage typeSize;

	public StationUI(Station s, Detail d) {
		
		add(d);
		
		init(s);
		
//		tabs.addTab(new Tab("Overview", new Overview(c), detail));
//		tabs.addTab(new Tab("Population", new Population(c.getResourceManager()), detail));
		
	}
	
	private void init(Station s) {
		
		tabs = new TabLayout(0, height+4, width, false);
		
		try {
			line = ImageIO.read(new File("gfx\\ui\\view\\line.png"));
			preview = ImageIO.read(new File("gfx\\ui\\view\\colony\\planet_preview.png"));
			typeSize  = ImageIO.read(new File("gfx\\ui\\view\\colony\\type_size.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public boolean action(Mouse m, Keyboard k) {
		
//		if(!m.buttonDown(1))
//			return false;

		int x = (int)Math.round(m.getPosition().getX());
		int y = (int)Math.round(m.getPosition().getY());
		
		if(0 < x && x < width) {
			if(0 < y && y < height) {
				tabs.selectedAction(m, k);
				return true;
			}
		}
		
		Mouse m1 = new Mouse(m, 0, height);
		
		if(tabs.action(m1, k))
			return true;
		
		return false;
		
	}

	public void render(Graphics g, int x1, int y1) {
		super.render(g, x1, y1);
		
		x1 = tabs.render(g, x, y);
		
		g.drawImage(line, x1-1, y+height, x+width-x1, 4, null);
		g.drawImage(preview, x+12, y+12, null);
		g.drawImage(typeSize, x+434+12, y+12, null);
		
		tabs.renderSelected(g, x, y);
		
	}

}
