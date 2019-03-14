package bodys;

import java.util.ArrayList;
import java.util.Random;

import Pulsar.Main;
import fleets.ActionType;
import species.colony.Colony;
import species.colony.Station;
import ui.universe.view.pane.Pane;
import ui.universe.view.pane.colony.ColonyUI;
import ui.universe.view.pane.detail.Detail;
import ui.universe.view.pane.list.ListAction;
import ui.universe.view.pane.station.StationUI;
import universe.StarSystem;

public class Body {

	protected int x;
	protected int y;
	protected int RenderRadius;
	
	protected double scale;
	
	protected boolean colonizable;
	
	protected String type;
	protected String colonyType;
	
	protected Random random;
	
	protected StarSystem starSystem;
	protected Colony colony;
	
	protected Station[] stations;
	
	public Body(StarSystem s) {
		
		starSystem = s;
		
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
				(mouseX > getX()+Main.WIDTH/2 - RenderRadius + screenX) && 
						(mouseX < getX()+Main.WIDTH/2 + RenderRadius + screenX)) && 
				((mouseY > getY()+Main.HEIGHT/2 - RenderRadius + screenY) && 
						(mouseY < getY()+Main.HEIGHT/2 + RenderRadius + screenY))
			) return true;
		
		return false;
		
	}

	/**
	 * gets a list of panes that the body has to desplay on screen
	 * @param detail 
	 * 
	 * @return the list of panes
	 */
	public ArrayList<Pane> getPanes(Detail detail) {
		
		ArrayList<Pane> panes = new ArrayList<Pane>();
		
		if(colony != null) {
			panes.add(new ColonyUI(colony, detail));
			for(Station s: stations)
				if(s != null)
					panes.add(new StationUI(s, detail));
		} else
			panes.add(new Pane());
		
		return panes;
		
	}

	public ArrayList<ListAction> getActions() {
		
		ArrayList<ListAction> a = new ArrayList<ListAction>();
		
		a.add(new ListAction(ActionType.MOVE_TO, this));
		a.add(new ListAction(ActionType.COLONIZE, this));
		
		return a;
	}
	
	//TODO make this an interface
	public boolean isColonizable() {
		return colonizable;
	}
	
	public boolean addStation(Station s) {
		
		if(stations[0] == null) {
			stations[0] = s;
		} else if(stations[1] == null) {
			stations[1] = s;
		} else {
			return false;
		}
		
		return true;
		
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

}
