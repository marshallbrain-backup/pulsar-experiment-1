package bodys;

import java.util.Random;

import pulsar.Main;
import species.colony.Colony;
import species.colony.Station;
import universe.StarSystem;

public class Body {

	protected int x;
	protected int y;
	protected int renderRadius;
	protected int stationCount;
	
	protected double scale;
	
	protected boolean colonizable;
	protected boolean reload;
	
	protected String type;
	protected String colonyType;
	
	protected Random random;
	
	protected StarSystem starSystem;
	protected Colony colony;
	
	protected Station[] stations;
	
	public Body(StarSystem s) {
		
		starSystem = s;
		
		stationCount = 0;
		reload = false;
		
		stations = new Station[2];
		random = new Random();
		
	}

	public int getX() {
		return (int)Math.round(x*scale);
	}

	public int getUnscaledX() {
		return x;
	}

	public int getY() {
		return (int)Math.round(y*scale);
	}

	public int getUnscaledY() {
		return y;
	}
	
	public String getType() {
		return type;
	}

	/**
	 * colition with the object
	 * 
	 * @param mouseX
	 * @param mouseY
	 * @param screenX
	 * @param screenY
	 * 
	 * @return is the point in side the body
	 */
	public boolean clicked(int mouseX, int mouseY, double screenX, double screenY) {
		
		if((
				(mouseX > getX()+Main.WIDTH/2 - renderRadius + screenX) && 
						(mouseX < getX()+Main.WIDTH/2 + renderRadius + screenX)) && 
				((mouseY > getY()+Main.HEIGHT/2 - renderRadius + screenY) && 
						(mouseY < getY()+Main.HEIGHT/2 + renderRadius + screenY))
			) return true;
		
		return false;
		
	}
	
	//TODO make this an interface
	public boolean isColonizable() {
		return colonizable;
	}
	
	public boolean addStation(Station s) {
		
		if(stationCount < stations.length) {
			stations[stationCount] = s;
			stationCount++;
			reload = true;
			return true;
		}
		
		return false;
		
	}

	public boolean tryAddStation() {
		return stationCount < stations.length;
	}

	public boolean addColony(Colony c) {
		if(colony == null) {
			colony = c;
			return true;
		}
		return false;
	}
	
	public Colony getColony() {
		return colony;
	}

	public String getColonyType() {
		return colonyType;
	}

	public StarSystem getSystem() {
		return starSystem;
	}

	public boolean shouldReload() {
		if(reload == true) {
			reload = false;
			return true;
		}
		return false;
	}

}
