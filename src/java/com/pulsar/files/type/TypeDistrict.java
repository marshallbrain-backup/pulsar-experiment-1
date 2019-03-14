package files.type;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import bodys.Body;
import bodys.Planet;
import files.GameFile;

public class TypeDistrict extends Type{
	
	private BufferedImage icon;

	public TypeDistrict(GameFile f) {
		super(f);
		
		if(!checkFile(f)) {
			System.out.println("DistrictType Failed");
			System.exit(0);
		}
		
		File i = new File("gfx\\ui\\view\\colony\\district\\" + name + ".png");
		
		if(!i.exists()) {
			i = new File("gfx\\ui\\view\\colony\\district\\" + "defalt" + ".png");
		}
		
		try {
			icon = ImageIO.read(i);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * checks requiered information in the gamefile. If something does not exist than abort the program
	 * 
	 * @param gameFile the gamefile being checked
	 */
	private boolean checkFile(GameFile f) {
		
		if(!isInt(f.getField("base_buildtime")))			return false;
		if(!isInt(f.getFieldOne("resources.cost")))			return false;
		if(!isInt(f.getFieldOne("resources.production")))	return false;
		
		return true;
		
	}
	
	public HashMap<String, String> getCost() {
		return gameFile.getFieldAll("resources.cost");
	}
	
	public HashMap<String, String> getUpkeep() {
		return gameFile.getFieldAll("resources.upkeep");
	}
	
	public HashMap<String, String> getProduction() {
		return gameFile.getFieldAll("resources.production");
	}

	/**
	 * does the type of district have the posibility of existing on the planet type
	 * 
	 * @param planet the planet being checked
	 */
	public boolean isPotential(Body b) {
		
		HashMap<String, String> potential = gameFile.getFieldAll("potential");
		for(String k: potential.keySet()) {
			if(k.equals("potential.is_planet_catagory")) {
				if(potential.get(k).equals(b.getColonyType())) {
					return true;
				}
			}
		}
		
		return false;
		
	}

	/**
	 * does the type of district start on the planet type
	 * 
	 * @param planet the planet being checked
	 */
	public boolean isStarting(Planet p) {
		
		HashMap<String, String> potential = gameFile.getFieldAll("starting");
		for(String k: potential.keySet()) {
			if(k.startsWith("starting.is_")) {
				if(potential.get(k).equals(p.getColonyType())) {
					return true;
				}
			}
		}
		
		return false;
	}

	public BufferedImage getIcon() {
		return icon;
	}

}
