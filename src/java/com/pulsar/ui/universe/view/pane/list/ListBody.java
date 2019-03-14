package ui.universe.view.pane.list;

import java.awt.Color;
import java.awt.Font;
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

public class ListBody {
	
	private BufferedImage entry;
	
	private Body body;
	
	public ListBody(Body b) {
		
		body = b;
		
		init();
		
	}
	
	private void init() {
		
		try {
			entry = ImageIO.read(new File("gfx\\ui\\view\\ship\\managment\\action_entry.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public Body action(Keyboard k, Mouse m) {
		
		int x = (int)Math.round(m.getPosition().getX());
		int y = (int)Math.round(m.getPosition().getY());
		
		if(0 < x && x < entry.getWidth()) {
			if(0 < y && y < entry.getHeight()) {
				return body;
			}
		}
		
		return null;
		
	}
	
	public int render(Graphics g, int x, int y) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
		
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage(entry, x, y, null);
		g2d.drawString(body.getType(), x+5, y+15);
		
		return y + entry.getHeight()+5;
		
	}
	
	public int getWidth() {
		return entry.getWidth();
	}
	
	public int getHeight() {
		return entry.getHeight();
	}

}
