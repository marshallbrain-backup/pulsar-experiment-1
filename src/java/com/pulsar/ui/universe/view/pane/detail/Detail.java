package ui.universe.view.pane.detail;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bodys.Body;
import input.Keyboard;
import input.Mouse;
import species.colony.ResourceManager;
import ui.universe.view.pane.Pane;

public class Detail {
	
	private int x;
	private int y;
	
	private String name;
	
	private BufferedImage tab;
	
	private ResourceManager resource;
	private Pane pane;
	
	/**
	 * creates a sepret view at the given cordanets
	 * 
	 * @param 
	 * @param x the x cordenet of the view
	 * @param y the y cordenet of the view
	 */
	public Detail(Body b, int x1, int y1) {
		
		resource = b.getColony().getResourceManager();
		
		x = x1+10;
		y = y1;
		
		name = "";
		
		try {
			tab = ImageIO.read(new File("gfx\\ui\\view\\colony\\detail.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setPane(Pane p) {
		setPane(p, "");
	}
	
	public void setPane(Pane p, String n) {
		pane = p;
		name = n;
	}
	
	public boolean action(Mouse m, Keyboard k) {
		
		if(!m.buttonDown(1) || pane == null)
			return false;

		int x1 = (int)Math.round(m.getPosition().getX())-x;
		int y1 = (int)Math.round(m.getPosition().getY())-y;
		
		if(0 < x1 && x1 < tab.getWidth()) {
			if(0 < y1 && y1 < tab.getHeight()) {
				pane.action(m, k);
				return true;
			}
		}
		
		return false;
		
	}
	
	public void render(Graphics g) {
		
		if(pane != null) {
			
			Graphics2D g2d = (Graphics2D)g;
			
			Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
			
			g2d.setFont(font);
			g2d.setColor(Color.WHITE);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			
			FontMetrics metrics = g.getFontMetrics(font);
			
			g.drawImage(tab, x, y, null);
			pane.render(g, x, y);
			g2d.setFont(font);
			g2d.drawString(name, x+(tab.getWidth()/2)-(metrics.stringWidth(name)/2), y+metrics.getHeight());
		}
	}

	public void setName(String n) {
		name = n;
	}

	public void close() {
		setPane(null);
	}
	
	public ResourceManager getResourceManager() {
		return resource;
	}

}
