package universe;

import java.awt.Graphics;

import files.type.TypeLoader;
import files.type.TypeSystem;
import input.Keyboard;
import input.Mouse;
import species.Species;

public class Galaxy {
	
	private StarSystem s;
	
	private TypeLoader systemLoader;

	/**
	 * initalizes Galaxy
	 * 
	 * @param gamefile gamefile
	 * @param species all speties starting in the galaxy
	 * @param keyboard keyboard class
	 * @param mouse mouse class
	 */
	public Galaxy(Species species, TypeLoader tl) {
		
		systemLoader = tl;
		
		init(species);
		
	}

	/**
	 * initalization for galaxy
	 */
	private void init(Species species) {
		
		int totalSystems = 1; //max systems in galaxy
		
		//loop for number of systems
		for(int i = 0; i < totalSystems; i++) {
			TypeSystem ts = (TypeSystem) systemLoader.getRandomType("systems"); //get a random system type
			s = new StarSystem(systemLoader, ts, species); //make a new system based on the type
		}
		
	}

	/**
	 * tick
	 * @param m 
	 * @param k 
	 */
	public void tick(Keyboard k, Mouse m) {
		s.tick(k, m);
	}

	/**
	 * render
	 * 
	 * @param graphics the graphics class for the canvas
	 */
	public void render(Graphics g) {
		s.render(g);
	}

}
