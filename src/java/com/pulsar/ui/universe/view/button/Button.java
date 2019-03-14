package ui.universe.view.button;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;

import javax.swing.GrayFilter;

import input.Keyboard;
import input.Mouse;

public class Button {
	
	int buttonX;
	int buttonY;
	
	boolean active;
	
	String name;

	private BufferedImage button;
	
	public Button(BufferedImage b, String n, int x, int y) {
		
		active = true;
		
		buttonX = x;
		buttonY = y;
		button = b;
		name = n;
		
	}

	public void setActive(boolean b) {
		active = b;
	}
	
	public boolean action(Mouse m, Keyboard k) {
		
		if(!m.buttonClicked(1))
			return false;
			
		int x = (int)Math.round(m.getPosition().getX()-buttonX);
		int y = (int)Math.round(m.getPosition().getY()-buttonY);
		
		if(!active)
			return false;
		
		if(x > 0 && x < button.getWidth()) {
			if(y > 0 && y < button.getHeight()) {
				return true;
			}
		}
		
		return false;
		
	}
	
	public void render(Graphics g) {
		render(g, 0, 0);
	}
	
	public void render(Graphics g, int x, int y) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 24);
		
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		FontMetrics metrics = g.getFontMetrics(font);
		
		if(!active) {
			ImageFilter filter = new GrayFilter(true, 25);  
			ImageProducer producer = new FilteredImageSource(button.getSource(), filter);  
			Image mage = Toolkit.getDefaultToolkit().createImage(producer);
			g.drawImage(mage, buttonX+x, buttonY+y, null);
		} else {
			g.drawImage(button, buttonX+x, buttonY+y, null);
		}
		
		g2d.drawString(name, buttonX+x+(button.getWidth()/2)-(metrics.stringWidth(name)/2), buttonY+y+(button.getHeight()/4)+(metrics.getHeight()/2));
		
	}

}
