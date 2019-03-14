package ui.universe.view.tab;

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

import input.Keyboard;
import input.Mouse;
import ui.universe.view.pane.Pane;
import ui.universe.view.pane.detail.Detail;

public class Tab {
	
	private int width;
	private int height;
	private int offsetX;
	private int offsetY;
	
	private boolean first;
	private boolean up;
	private boolean selected;
	
	private String name;
	
	private BufferedImage tab;
	
	private Pane pane;
	
	public Tab(String n, Pane p, Detail d) {
		init(n, p, d);
	}

	public void init(String n, Pane p, Detail d) {
		
		first = false;
		selected = false;
		name = n;
		up = true;
		
		pane = p;
		
		pane.add(d);
		drawTab();
		
	}

	/**
	 * draws the tabs to a buffered image, saves lots of time
	 */
	private void drawTab() {
		
		int x = 0;
		int y = 0;
		
		offsetX = 0;
		offsetY = 0;
		
		BufferedImage line = null;
		BufferedImage end = null;
		
		//load requiered graphicts
		try {
			line = ImageIO.read(new File("gfx\\ui\\view\\line.png"));
			if(up) {
				if(selected)
					end = ImageIO.read(new File("gfx\\ui\\view\\tab_top_1.png"));
				else
					end = ImageIO.read(new File("gfx\\ui\\view\\tab_top_2.png"));
				offsetY = 0;
			} else {
				if(selected)
					end = ImageIO.read(new File("gfx\\ui\\view\\tab_bot_1.png"));
				else
					end = ImageIO.read(new File("gfx\\ui\\view\\tab_bot_2.png"));
				offsetY = end.getHeight()-8;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FontMetrics fm = line.getGraphics().getFontMetrics(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		int nameWidth = fm.stringWidth(name);
		width = nameWidth + 10 + end.getWidth(); //set width based on text size
		if(!first)
			offsetX = end.getWidth();
			width += offsetX;
		height = 50;
		
		tab = new BufferedImage(width, height+4, BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = tab.getGraphics();
		
		//draws left most line
		if(first) {
			g.drawImage(line, x, y, 4, height, null);
		} else {
			g.drawImage(line, x, y+((up) ? 0: height), end.getWidth(), 4, null);
			x += end.getWidth();
		}
		
		//draws to line
		g.drawImage(line, x, y+((up) ? 0: height), nameWidth+10, 4, null);
		
		//draws bottom line if tab is not selected
		if(!selected)
			g.drawImage(line, x-1, y+((up) ? height: 0), nameWidth+2+end.getWidth(), 4, null);
		
		x += nameWidth+10;
		
		//draws end diagonal
		g.drawImage(end, x, y, null);
		
		g.dispose();
		
		drawBackground(line.getRGB(0, 0), (first) ? null: end);
		
		g = tab.getGraphics();
		
		//draws tab name
		Graphics2D g2d = (Graphics2D)g;
		g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		g2d.setColor(Color.WHITE);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(first) {
			g2d.drawString(name, 10, 40);
		} else {
			g2d.drawString(name, offsetX, 40);
		}
		
		g.dispose();
		
	}

	/**
	 * draws tab background
	 */
	private void drawBackground(int c, BufferedImage end) {
		
		//creats new image for drawing backgrond on
		BufferedImage newTab = new BufferedImage(tab.getWidth(), tab.getHeight(), BufferedImage.TYPE_INT_ARGB);
		int background = 0;
		
		//gets tab background color
		try {
			background = ImageIO.read(new File("gfx\\ui\\view\\background.png")).getRGB(0, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//draws the background
		for(int y = 4; y < newTab.getHeight()-4; y++) {
			
			boolean draw = false; //draw background for current y line
			boolean stop = false; //skip to next y line
			
			//loop through x line
			for(int x = 4; x < newTab.getWidth(); x++) {
				
				//if not currently drawing and not first or reached a diagonal line then begin drawing
				if(!draw && (end == null || end.getRGB(x, y) == c))
					draw = true;
				
				//if not drawing then skip to next x
				if(!draw)
					continue;
				
				//draw background for cordenet
				newTab.setRGB(x, y, background);
				
				//if transparent at cordenets in tab then give ability to stop
				if(draw && tab.getRGB(x, y) == 0)
					stop = true;
				
				//next diagonal line break from current x line
				if(stop && tab.getRGB(x, y) == c)
					break;
				
			}
			
		}
		
		Graphics g = newTab.getGraphics();
		
		g.drawImage(tab, 0, 0, null);
		g.dispose();
		
		tab = newTab;
		
	}
	
	public void setFirst(boolean f) {
		first = f;
		drawTab();
	}
	
	public void setSelected(boolean s) {
		selected = s;
		drawTab();
	}
	
	public void setOriantation(boolean u) {
		up = u;
		drawTab();
	}

	/**
	 * if position is not transparent than regester click
	 */
	public boolean clicked(int x1, int y1) {
		
		x1 += offsetX;

		if(y1 > 0 && y1 < height) {
			if(x1 > 0 && x1 < width) {
				if(tab.getRGB(x1, y1) != 0) {
//					System.out.println(name);
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	public int getWidth() {
		return width-offsetX;
	}

	/**
	 * draw tab
	 */
	public int render(Graphics g, int x, int y) {
		x -= width;
		y += offsetY;
		
//		g.drawImage(bounds, x, y, null);
		g.drawImage(tab, x, y, null);
		return x+offsetX;
	}
	
	public void renderPane(Graphics g, int x, int y) {
		pane.render(g, x, y);
	}

	public boolean paneAction(Mouse m, Keyboard k) {
		return pane.action(m, k);
	}

}
