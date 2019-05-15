package fleets;

import java.awt.Point;
import java.util.ArrayList;

import species.Species;
import universe.StarSystem;

public class Ship {
	
	private int size;
	private int speed;

	private double x;
	private double y;
	private double entityScale;
	private double scale;
	
	private ArrayList<Action> actionQueue;
	
	private StarSystem currentSystem;
	private Species species;
	
	public Ship(Species s) {
		
		x = 50;
		y = 50;
		scale = 0;
		entityScale = 8;
		size = 10;
		speed = 1;
		
		species = s;
		
		actionQueue = new ArrayList<Action>();
		
	}
	
	public void setSystem(StarSystem s) {
		if(currentSystem != null)
			currentSystem.shipExit(this);
		currentSystem = s;
		currentSystem.shipEnter(this);
	}

	public StarSystem getSystem() {
		return currentSystem;
	}
	
	public void update(long update) {
		
		if(!actionQueue.isEmpty()) {
			
			Action currentAction = actionQueue.get(0);
			
			switch(currentAction.getAction()) {
			case MOVE_TO:
				moveShip(currentAction);
				break;
			case COLONIZE:
				if(moveShip(currentAction))
					colonize(currentAction);
				break;
			default:
				break;
			}
			
		}
		
	}
	
	private void colonize(Action currentAction) {
		species.createColony(currentAction.getBody());
		actionQueue.remove(0);
	}

	private boolean moveShip(Action currentAction) {
		
		double tx = currentAction.getBody().getUnscaledX();
		double ty = currentAction.getBody().getUnscaledY();
		
		Point t = new Point();
		t.setLocation(tx, ty);
		
		//TODO better collision detection
		if((t.distance(x, y)) < speed) {
			x = tx;
			y = ty;
			if(currentAction.getAction() == ActionType.MOVE_TO)
				actionQueue.remove(0);
			return true;
		}
		
		double dx = tx-x;
		double dy = ty-y;
		
		double angle = Math.atan(dy/dx);
		
		double mx = speed*Math.cos(angle);
		double my = speed*Math.sin(angle);

		if(dy < 0) {
			my = Math.abs(my);
		} else {
			my = -Math.abs(my);
		}
		if(dx < 0) {
			mx = Math.abs(mx);
		} else {
			mx = -Math.abs(mx);
		}
		
		x = (x - mx);
		y = (y - my);
		
		return false;
		
	}

	public int getX() {
		return (int)Math.round(x*scale);
	}

	public int getUnscaledX() {
		return (int) x;
	}

	public int getY() {
		return (int)Math.round(y*scale);
	}

	public int getUnscaledY() {
		return (int) y;
	}

	public void addAction(Action a) {
		actionQueue.add(a);
		System.out.println(a);
	}

	public void removeAction(int i) {
		actionQueue.remove(i);
	}

	public ArrayList<Action> getActionList() {
		return actionQueue;
	}

}
