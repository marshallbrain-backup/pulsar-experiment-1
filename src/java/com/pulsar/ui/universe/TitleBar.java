package ui.universe;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import Pulsar.Main;
import species.colony.ResourceManagerMaster;
import ui.universe.view.pane.Resorce;
import universe.Clock;

public class TitleBar {
	
	private BufferedImage line;
	private BufferedImage background;
	
	private ArrayList<String> resorcesDisplay;
	
	private Clock clock;
	private ResourceManagerMaster master;
	
	public TitleBar(ResourceManagerMaster r, Clock c) {
		
		master = r;
		clock = c;
		
		try {
			line = ImageIO.read(new File("gfx\\ui\\view\\line.png"));
			background = ImageIO.read(new File("gfx\\ui\\view\\background.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
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

	public void render(Graphics g) {
		
		g.drawImage(line, 0, 50,  Main.WIDTH, 4, null);
		g.drawImage(background, 0, 0, Main.WIDTH, 50, null);
		
		clock.render(g);
		
		renderResources(g);
		
	}

	public void renderResources(Graphics g) {
		renderResorces(g, 50, 6, master.getResourceTotal(), master.getResourceIncome());
	}
	
	private void renderResorces(Graphics g, int x, int y, HashMap<String, Double> renorce, HashMap<String, Double> renorceIncome) {
		for(String r: resorcesDisplay) {
			x = Resorce.render(g, x, y, r, master.getResourceTotal().getOrDefault(r, 0.0),renorceIncome.getOrDefault(r, 0.0));
		}
	}

}
