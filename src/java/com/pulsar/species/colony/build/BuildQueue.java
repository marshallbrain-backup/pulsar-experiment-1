package species.colony.build;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class BuildQueue {
	
	private int buildInstincese;
	
	private ArrayList<BuildQueueEntry> buildFuctions;
	
	private BufferedImage queue;
	private BufferedImage item;
	
	public BuildQueue() {
		
		buildInstincese = 1;
		
		buildFuctions = new ArrayList<BuildQueueEntry>();
		
		try {
			queue = ImageIO.read(new File("gfx\\ui\\view\\colony\\build_queue.png"));
			item = ImageIO.read(new File("gfx\\ui\\view\\colony\\build_info.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void add(BuildQueueEntry b) {
		buildFuctions.add(b);
	}

	public void update(int update) {
		
		int bI = Math.min(buildFuctions.size(), buildInstincese);
		
		for(int i = 0; i < bI; i++) {
			if(buildFuctions.get(i).buildTick(update)) {
				buildFuctions.remove(i);
			}
		}
		
	}

	public void render(Graphics g, int x, int y) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		g2d.setColor(Color.WHITE);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage(queue, x, y, null);
		
		x += 5;
		y += 35;
		
		for(int i = 0; i < buildFuctions.size(); i++) {

			g.drawImage(item, x, y, null);
			y += item.getHeight() + 5;
		}
		
	}

}
