package universe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import Pulsar.Main;
import bodys.Body;
import bodys.Planet;
import bodys.Star;
import files.type.Type;
import files.type.TypeLoader;
import files.type.TypePlanet;
import files.type.TypeStar;
import files.type.TypeSystem;
import fleets.Ship;
import input.Keyboard;
import input.Mouse;
import species.Species;
import ui.universe.view.View;
import ui.universe.view.ViewBody;
import ui.universe.view.ViewFleet;

public class StarSystem {
	
	private int zoom;
	private int systemRadius;
	
	private static double zoomStep;
	
	private boolean moving;
	
	private Point systemCenter;
	private Point zoomCenter;
	
	private ArrayList<Star> starList;
	private ArrayList<Planet> planetList;
	private ArrayList<Ship> fleetList;
//	private ArrayList<View> viewList;
	
	protected Random random;

	private Body selectedBody;
	private View view;
	
	private TypeSystem systemType;
	private TypeLoader loader;
	
	/**
	 * initalise starsystem
	 * 
	 * @param gamefile the gamefile
	 * @param typeloader the typeloader with system
	 * @param species
	 * @param keyboard
	 * @param mouse
	 */
	public StarSystem(TypeLoader tl, TypeSystem ts, Species s) {
		
		loader = tl;
		systemType = ts;
		
		zoom = 1;
		zoomStep = 2;
		
		starList = new ArrayList<Star>();
		planetList = new ArrayList<Planet>();
		fleetList = new ArrayList<Ship>();
//		viewList = new ArrayList<View>();
		random = new Random();
		systemCenter = new Point(0, 0);
		zoomCenter = new Point(0, 0);
		
		init();
		
		ArrayList<Planet> colonize = new ArrayList<Planet>();
		
		//adds all colonizable planets to a list
		for(Planet p: planetList) {
			if(p.isColonizable()) {
				colonize.add(p);
			}
		}
		
		//picks one to colonize
		int id = random.nextInt(colonize.size());
		
		s.createColony(colonize.get(id));
		s.createStation(colonize.get(id));
		//viewList.add(new ViewBody(colonize.get(id)));
		
	}

	/**
	 * initalises the star system
	 */
	private void init() {
		
//		TODO: change distence to realistic distences
		
		//add all stars for the given system to the list
		for(Type s: loader.getTypes("stars", systemType.getStarClasses().split(","))) {
			starList.add(new Star((TypeStar) s, this));
		}
		
		generatePlanets(systemType.getPlanetNumber());
		
	}

	/**
	 * generates planets
	 * 
	 * @param max max number of planets
	 */
	private void generatePlanets(int max) {
		
		for(int i = 0; i < max; i++) {
			int r = 10+i*50;
			planetList.add(new Planet((TypePlanet) loader.getRandomType("planets"), this, r));
			if(systemRadius < r)
				systemRadius = r;
		}
		
	}

	public ArrayList<Planet> getPlanets() {
		return planetList;
	}
	
	public void tick(Keyboard k, Mouse m) {
		
		if(moving && !m.buttonDown(1))
			moving = false;
		
		if(!moving) {
			if(view != null) {
				if(view.action(m, k)) {
					if(view.toClose())
						view = null;
					return;
				}
			}
		}
		
		int zoomDir = m.getWheelDir()*-1;
		
		if(m.buttonClicked(1) && selectObject(m)) {
			return;
		}
		if(zoomDir != 0) {
			zoom(zoomDir, m);
			return;
		}
		if(m.buttonDown(1)) {
			drag(m);
			moving = true;
			return;
		}
		
	}
	
	private boolean selectObject(Mouse m) {
		
		int mouseX = (int)Math.round(m.getPosition().getX());
		int mouseY = (int)Math.round(m.getPosition().getY());
		
		for(Ship s: fleetList) {
			if(s.clicked(mouseX, mouseY, systemCenter.getX(), systemCenter.getY())) {
				
	//			x = -p.getX();
	//			y = -p.getY();
	//			
	//			if(selectedBody == p && viewList.isEmpty())
	//				viewList.add(new ViewBody(selectedBody));
	//			else
	//				selectedBody = p;
				
				view = new ViewFleet(s);
			
				return true;
				
			}
		}

		//selects click planet
		for(Planet p: planetList) {
			if(p.clicked(mouseX, mouseY, systemCenter.getX(), systemCenter.getY())) {
			
//					x = -p.getX();
//					y = -p.getY();
//					
//					if(selectedBody == p && viewList.isEmpty())
//						viewList.add(new ViewBody(selectedBody));
//					else
//						selectedBody = p;
				
				view = new ViewBody(p);
				
				return true;
				
			}
		}
		
		for(Star s: starList) {
			if(s.clicked(mouseX, mouseY, systemCenter.getX(), systemCenter.getY())) {
				
//					x = s.getX();
//					y = s.getY();
//					
//					if(selectedBody == s && viewList.isEmpty())
//						viewList.add(new ViewBody(selectedBody));
//					else
//						selectedBody = s;
				
				view = new ViewBody(s);
				
				return true;
				
			}
		}
		
		return false;
		
	}
	
	private void drag(Mouse m) {
		
		int mouseX = (int)Math.round(m.getChange().getX());
		int mouseY = (int)Math.round(m.getChange().getY());
		
		if(!(mouseX == 0 && mouseY == 0)) {
			systemCenter.translate(-mouseX, -mouseY);
//			System.out.println(systemCenter);
		}
		
	}
	
	private void zoom(int zoomDir, Mouse m) {
		
		int z = zoom+zoomDir;
		boolean d = z < zoom;
		zoom = z;
		
		if(zoom < 1) {
			zoom = 1;
			return;
		}
		if(zoom > 50) {
			zoom = 50;
			return;
		}
		
		int targetX = (int)Math.round(m.getPosition().getX())-Main.WIDTH/2;
		int targetY = (int)Math.round(m.getPosition().getY())-Main.HEIGHT/2;
		
//		if(!d) {
//			targetX *= -1;
//			targetY *= -1;
//		}
		
		//TODO better zoom
		
		Point zCenter = new Point();
//		zCenter.setLocation((zoomCenter.getX())/(zoom-zoomDir)-targetX, (zoomCenter.getY())/(zoom-zoomDir)-targetY);
		
		Point sCenter = new Point();
		sCenter.setLocation((systemCenter.getX())/(zoom-zoomDir), (systemCenter.getY())/(zoom-zoomDir));
		
//		zoomCenter.setLocation(targetX+sCenter.getX(), targetY+sCenter.getY());
		
		systemCenter.setLocation((sCenter.getX())*zoom, (sCenter.getY())*zoom);
		
	}
	
	//TODO move to sepret ui class
	public void render(Graphics g) {
		
		//render all stars
		for(Star s: starList) {
			s.render(g, systemCenter.getX(), systemCenter.getY(), zoom);
		}
		
		//render all planets
		for(Planet p: planetList) {
			p.render(g, systemCenter.getX(), systemCenter.getY(), zoom);
		}

		for(Ship s: fleetList) {
			s.render(g, systemCenter.getX(), systemCenter.getY(), zoom);
		}
		
		//render all views
		if(view != null)
			view.render(g);
		
		g.setColor(Color.BLACK);
		
		g.fillRect(Main.WIDTH/2-5, Main.HEIGHT/2-5, 10, 10);
		
	}
	
	public void shipEnter(Ship s) {
		fleetList.add(s);
	}
	
	public void shipExit(Ship s) {
		fleetList.remove(s);
	}

}
