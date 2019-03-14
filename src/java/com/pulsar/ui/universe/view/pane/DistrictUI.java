package ui.universe.view.pane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;

import input.Keyboard;
import input.Mouse;
import species.colony.build.District;

public class DistrictUI {
	
	private int x;
	private int y;

	private BufferedImage border;
	
	private District district;

	public DistrictUI(District d) {
		
		district = d;
		
		try {
			border = ImageIO.read(new File("gfx\\ui\\view\\colony\\district\\boarder.png")); //loads the boarder for each district
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * did u click this?
	 */
	public boolean action(Mouse m, Keyboard k, int i) {
		
		if(!m.buttonDownOnce(1))
			return false;

		int x = (int)Math.round(m.getPosition().getX()-this.x);
		int y = (int)Math.round(m.getPosition().getY()-this.y);
		
		x -= (i%4)*80;
		y -= (i/4)*100;
		
		if(0 < x && x < border.getWidth()) {
			if(0 < y && y < border.getHeight()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * renders the district in the view
	 * 
	 * @param graphicts
	 * @param index the index position that the district is in
	 * @param x the x cordenet that the districts are ofset from
	 * @param y the y cordenet that the districts are ofset from
	 */
	public boolean render(Graphics g, int i, int x1, int y1) {
		
		x = x1;
		y = y1;
		
		//generates the offset cordenets
		int dx = (i%4)*80; //max of 4 district renderd on a line spaced 80 pixels apart in x direction
		int dy = (i/4)*100; //verticaly spased by 100 pixels
		
		g.drawImage(border, x+dx, y+dy, null);
		
		//if district is not null then draw the district icon
		
		if(district.getType() != null) {// || 
			
			g.drawImage(district.getType().getIcon(), x+dx+1, y+dy+1, null);
			
			Graphics2D g2d = (Graphics2D)g;
			
			g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
			g2d.setColor(Color.WHITE);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			
			g2d.drawString(String.format("%02d", district.getQuantaty()) + "/" + String.format("%02d", district.getMaxQuantaty()), x+dx+10, y+dy+92);
			
			return true;
			
		} else if(district.getPendingType() != null) {
			ImageFilter filter = new GrayFilter(true, 25);  
			ImageProducer producer = new FilteredImageSource(district.getPendingType().getIcon().getSource(), filter);  
			Image mage = Toolkit.getDefaultToolkit().createImage(producer);
			g.drawImage(mage, x+dx+1, y+dy+1, null);
			return true;
		}
		
		return false;
		
	}

	public int getBoarderWidth() {
		return border.getWidth();
	}

	public int getBoarderHeight() {
		return border.getHeight();
	}

	public District getDistrict() {
		return district;
	}

}
