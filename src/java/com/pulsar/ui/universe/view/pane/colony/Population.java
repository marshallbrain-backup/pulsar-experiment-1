package ui.universe.view.pane.colony;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import files.type.TypeJob;
import input.Keyboard;
import input.Mouse;
import species.colony.Job;
import species.colony.Pop;
import species.colony.ResourceManager;
import ui.universe.view.pane.Pane;

public class Population extends Pane{
	
	private float lastUpdate;
	
	private HashMap<String, HashMap<String, ArrayList<Job>>> jobList;
	private HashMap<String, ArrayList<Pop>> unemployed;
	
	private BufferedImage popIcon;
	private BufferedImage jobIcon;
	private BufferedImage jobCatogory;
	private BufferedImage boarder;
	
	private ResourceManager resource;

	public Population(ResourceManager r) {
		
		lastUpdate = 0;
		
		resource = r;
		
		jobList = new HashMap<String, HashMap<String, ArrayList<Job>>>();
		unemployed = new HashMap<String, ArrayList<Pop>>();
		
		try {
			popIcon = ImageIO.read(new File("gfx\\ui\\view\\colony\\pops.png"));
			jobIcon = ImageIO.read(new File("gfx\\ui\\view\\colony\\jobs.png"));
			jobCatogory = ImageIO.read(new File("gfx\\ui\\view\\colony\\job_catogory.png"));
			boarder = ImageIO.read(new File("gfx\\ui\\view\\colony\\boarder.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean action(Mouse m, Keyboard k) {
		return false;
	}
	
	public void update() {
		
		HashMap<TypeJob, ArrayList<Job>> jList = resource.getJobList();
		unemployed = new HashMap<String, ArrayList<Pop>>(resource.getUnemploymentList());
		
		for(Entry<TypeJob, ArrayList<Job>> c: jList.entrySet()) {
			HashMap<String, ArrayList<Job>> cat = jobList.get(c.getKey().getCatagory());
			if(cat == null)
				cat = new HashMap<String, ArrayList<Job>>();
			cat.put(c.getKey().getName(), c.getValue());
			jobList.putIfAbsent(c.getKey().getCatagory(), cat);
		}
		
		for(Entry<String, ArrayList<Pop>> u: unemployed.entrySet()) {
			jobList.putIfAbsent(u.getKey(), null);
		}
		
		lastUpdate = System.nanoTime();
		
	}

	public void render(Graphics g, int x, int y) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 22);
		
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		//sets offset from cornor of view
		x += 12;
		y += 12;
		
		g.drawImage(popIcon, x, y+210, null);
		g2d.drawString("Pops", x+6, y+235);
		
		x += 270;
		y += 210;
		
		g.drawImage(jobIcon, x, y, null);
		g2d.drawString("Jobs", x+142, y+22);

		x += 5;
		y += 30;
		
		if(lastUpdate < resource.getLastUpdate() || lastUpdate == 0) {
			update();
		}
		
		for(Entry<String, HashMap<String, ArrayList<Job>>> j: jobList.entrySet()) {
			if(j.getValue() != null)
				y = renderJobList(g, x, y, new TreeMap<String, ArrayList<Job>>(j.getValue()));
			y = renderUnemployed(g, x, y, unemployed.get(j.getKey()), j.getKey());
		}
		
	}
	
	private int renderJobList(Graphics g, int x, int y, TreeMap<String, ArrayList<Job>> jobs) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		FontMetrics metrics = g.getFontMetrics(g2d.getFont());
		
		for(Entry<String, ArrayList<Job>> t: jobs.entrySet()) {
			int y1 = y+25;
			if(!t.getValue().isEmpty()) {
				
				HashMap<String, Integer> n = new HashMap<String, Integer>();
				
				g.drawImage(jobCatogory, x, y, null);
				
				Font font = g2d.getFont();
				
				g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
				g2d.drawString(t.getKey(), x+5, y+15);
				g2d.setFont(font);
				
				int x1 = x+55;
				for(Job j: t.getValue()) {
					int i = 0;
					if(j.getPop() != null) {
						i = n.getOrDefault(j.getPop().toString(), 0);
					} else {
						i = n.getOrDefault("unworked", 0);
					}
					
					if(i == 0) {
						g.drawImage(boarder, x1, y1, null);
						x1 += boarder.getWidth()+5;
					}
					i++;
					
					if(j.getPop() != null) {
						n.put(j.getPop().toString(), i);
					} else {
						n.put("unworked", i);
					}
					
				}

				int x2 = x+55+boarder.getWidth()-5;
				int y2 = y1+boarder.getHeight()-8;
				for(int i: n.values()) {
					g2d.drawString(String.valueOf(i), x2-(metrics.stringWidth(String.valueOf(i))), y2);
					x2 += boarder.getWidth()+5;
				}
				
				y += jobCatogory.getHeight()+5;
			}
		}

		return y;
		
	}
	
	private int renderUnemployed(Graphics g, int x, int y, ArrayList<Pop> un, String cat) {
		
		Graphics2D g2d = (Graphics2D)g;

		g.drawImage(jobCatogory, x, y, null);
		
		Font font = g2d.getFont();
		
		g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
		g2d.drawString(cat, x+5, y+15);
		g2d.setFont(font);
		
		FontMetrics metrics = g.getFontMetrics(font);
		
		x+=55;
		y+=25;
		int x1 = x;
		
		HashMap<String, Integer> n = new HashMap<String, Integer>();
		
		for(Pop p: un) {
			int i = n.getOrDefault(p.toString(), 0);
			if(i == 0) {
				g.drawImage(boarder, x1, y, null);
				x1 += boarder.getWidth()+5;
			}
			i++;
			n.put(p.toString(), i);
		}

		x1 = x+boarder.getWidth()-5;
		int y1 = y+boarder.getHeight()-8;
		for(int i: n.values()) {
			g2d.drawString(String.valueOf(i), x1-(metrics.stringWidth(String.valueOf(i))), y1);
			x1 += boarder.getWidth()+5;
		}

		return y;
		
	}

}
