package universe;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Clock {
	
	private long lastSecond;
	private long timeIncriment;
	
	private Calender calender;
	
//	private BufferedImage pause;
//	private BufferedImage play;
	
	public Clock() {
		
		calender = new Calender(2000);
		
//		try {
//			pause  = ImageIO.read(new File("gfx\\ui\\view\\title\\pause.png"));
//			play  = ImageIO.read(new File("gfx\\ui\\view\\title\\play.png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		timeIncriment = 86400;
		lastSecond = System.nanoTime() / 1000000000;
		
	}

	public long tick() {
		
		long thisSecond = System.nanoTime() / 1000000000;
		
		if (thisSecond > lastSecond) {
			lastSecond = thisSecond;
			
			calender.incriment(timeIncriment);
			return timeIncriment;
			
		}
		
		return 0;
		
	}
	
	public void render(Graphics g) {
		
		calender.render(g);
		
//		g.drawImage(pause, Main.WIDTH-play.getWidth()-45, 10, null);
//		g.drawImage(play, Main.WIDTH-40, 9, null);
		
	}

}
