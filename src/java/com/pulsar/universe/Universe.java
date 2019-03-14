package universe;

import java.awt.Graphics;

import files.GameFile;
import files.type.TypeBuilding;
import files.type.TypeCategory;
import files.type.TypeDistrict;
import files.type.TypeJob;
import files.type.TypeLoader;
import files.type.TypePlanet;
import files.type.TypeStar;
import files.type.TypeSystem;
import input.Keyboard;
import input.Mouse;
import species.Species;
import ui.universe.TitleBar;
import ui.universe.Tooltip;
import ui.universe.view.pane.Resorce;

public class Universe {
	
	private Galaxy galaxy;
	
	private Species species;
	private Clock clock;
	private TitleBar titleBar;
	
	/**
	 * initalizes Universe
	 * 
	 * @param save the save game file
	 * @param keyboard keyboard class
	 * @param mouse mouse class
	 * @param gamefile gamefile
	 */
	public Universe(String save, GameFile gf) {
		
		TypeLoader speciesLoader = new TypeLoader();
		
		speciesLoader.addTypes("district", gf.getFieldAll("districts\\..*"), 2, TypeDistrict.class);
		speciesLoader.addTypes("building", gf.getFieldAll("buildings\\..*"), 2, TypeBuilding.class);
		speciesLoader.addTypes("job", gf.getFieldAll("pop_job\\..*"), 2, TypeJob.class);
		speciesLoader.addTypes("category", gf.getFieldAll("pop_categories\\..*"), 2, TypeCategory.class);
		
		species = new Species(speciesLoader);
		
		init(gf);
		
	}
	
	/**
	 * initalization for universe
	 */
	private void init(GameFile gf) {
		
		TypeLoader systemLoader = new TypeLoader();
		
		//preloads all type classes for systems, stars, planets
		systemLoader.addTypes("systems", gf.getFieldAll("system_classes\\..*"), 2, TypeSystem.class);
		systemLoader.addTypes("stars", gf.getFieldAll("star_classes\\..*"), 2, TypeStar.class);
		systemLoader.addTypes("planets", gf.getFieldAll("planet_classes\\..*"), 2, TypePlanet.class);
		
		galaxy = new Galaxy(species, systemLoader);
		clock = new Clock();
		titleBar = new TitleBar(species.getResourceManagerMaster(), clock);
		
		Resorce.init();
		Tooltip.init();
		
	}
	
	/**
	 * tick meathod
	 * @param m 
	 * @param k 
	 */
	public void tick(Keyboard k, Mouse m) {
		
		long update = clock.tick();
		
		galaxy.tick(k, m);
		species.update(update);
		
	}
	
	/**
	 * render meathod
	 * 
	 * @param graphics the graphics class for the canvas
	 */
	public void render(Graphics g) {
		galaxy.render(g);
		titleBar.render(g);
		Tooltip.render(g);
	}

}
