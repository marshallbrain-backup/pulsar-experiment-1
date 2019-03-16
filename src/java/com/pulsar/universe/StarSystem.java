package universe;

import java.util.ArrayList;
import java.util.Random;

import bodys.Planet;
import bodys.Star;
import files.type.Type;
import files.type.TypeLoader;
import files.type.TypePlanet;
import files.type.TypeStar;
import files.type.TypeSystem;
import fleets.Ship;
import species.Species;

public class StarSystem {
	
	private int systemRadius;
	
	private ArrayList<Star> starList;
	private ArrayList<Planet> planetList;
	private ArrayList<Ship> fleetList;
//	private ArrayList<View> viewList;
	
	protected Random random;
	
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
		
		starList = new ArrayList<Star>();
		planetList = new ArrayList<Planet>();
		fleetList = new ArrayList<Ship>();
//		viewList = new ArrayList<View>();
		random = new Random();
		
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
	
	public void tick() {
		
	}
	
	public void shipEnter(Ship s) {
		fleetList.add(s);
	}
	
	public void shipExit(Ship s) {
		fleetList.remove(s);
	}

}
