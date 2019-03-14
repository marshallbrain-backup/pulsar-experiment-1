package ui.universe;

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

public class Tooltip {
	
	private static int x;
	private static int y;
	
	private static boolean render;
	
	private static String tip;
	
	private static BufferedImage line;
	private static BufferedImage background;
	
	public static void init() {
		
		render = false;
		
		try {
			line = ImageIO.read(new File("gfx\\ui\\view\\lineBlue.png"));
			background = ImageIO.read(new File("gfx\\ui\\view\\background.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public static void set(Graphics2D g, String t, int x1, int y1) {
		
		x = (int) (g.getTransform().getTranslateX()+x1)+25;
		y = (int) (g.getTransform().getTranslateY()+y1)+25;
		tip = t;
		render = true;
		
	}
	
	public static void render(Graphics g) {
		
		if(!render)
			return;
		
		int width = 0;
		int height = 0;
		
		Graphics2D g2d = (Graphics2D)g;
		
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
		
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		FontMetrics metrics = g.getFontMetrics(font);
		
		for (String line : tip.split("\n")) {
			height += metrics.getHeight();
			int w = metrics.stringWidth(line);
			if(width < w)
				width = w;
		}
		
		width += 10;
		height += 10;
		
		g.drawImage(background, x, y, width, height, null);

		g.drawImage(line, x      , y       , width, 1     , null);
		g.drawImage(line, x+width, y       , 1    , height, null);
		g.drawImage(line, x      , y+height, width, 1     , null);
		g.drawImage(line, x      , y       , 1    , height, null);
		
		for (String line : tip.split("\n"))
			g2d.drawString(line, x+5, y += g.getFontMetrics().getHeight());
		
		render = false;
		
	}

}
