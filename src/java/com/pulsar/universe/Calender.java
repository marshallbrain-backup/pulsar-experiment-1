package universe;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import Pulsar.Main;

public class Calender {
	
	private int startYear;
	
	private long totalSeconds;
	
	public Calender(int y) {
		
		startYear = y;
		
	}
	
	public long incriment(long s) {
		totalSeconds += s;
		return totalSeconds;
	}
	
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 24);
		
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		FontMetrics metrics = g.getFontMetrics(font);
		
		g2d.drawString(String.valueOf(totalSeconds), Main.WIDTH-metrics.stringWidth(String.valueOf(totalSeconds))-10, 30);
		
	}

}
