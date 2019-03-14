package ui.universe.view.pane;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Resorce {
	
	private static HashMap<String, BufferedImage> resorces;
	
	public static void init() {
		
		resorces = new HashMap<String, BufferedImage>();
		
		File res = new File("gfx\\ui\\icon\\resources");
		
		for(File r: res.listFiles()) {
			try {
				resorces.put(r.getName().split("\\.")[0], ImageIO.read(r));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static int render(Graphics g, int x, int y, int maxX, String type, double amount) {
		
		if(amount == 0)
			return 0;
		
		Graphics2D g2d = (Graphics2D)g;
		
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 24);
		
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		FontMetrics metrics = g.getFontMetrics(font);
		
		g.drawImage(resorces.getOrDefault(type, resorces.get("unknown")), x+10, y+37, null);
		g2d.drawString(String.valueOf(amount), x+34, y+54);
		
		x += 34+metrics.stringWidth(String.valueOf(amount));
		
		if(x >= maxX)
			return 0;
		return x;
		
	}

	public static int render(Graphics g, int x, int y, String type, double total, double income) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
		
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		FontMetrics metrics = g.getFontMetrics(font);
		
		String in = " " + (((income >= 0)? "+": "-") + numberToString(income));
		String to = numberToString(total);
		
		x += 30;
		
		g.drawImage(resorces.getOrDefault(type, resorces.get("unknown")), x, y, null);
		g2d.drawString(to, x-metrics.stringWidth(String.valueOf(to)), y+38);
		g2d.drawString(in, x, y+38);
		
		x += 50;
		
		return x;
		
	}
	
	private static String numberToString(double amount) {
		
		String a = "";
		
		if(amount >= 1000) {
			if(amount >= 10000)
				a = (String.valueOf(Math.round(amount/1000)))+"K";
			else
				a = (String.valueOf(Math.round(amount/100)/10.0))+"K";
		} else {
			a = String.valueOf(Math.round(amount));
		}
		
		return a;
		
	}

}
