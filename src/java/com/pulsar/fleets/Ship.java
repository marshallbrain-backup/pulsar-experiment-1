package fleets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Pulsar.Main;
import gfx.Draw;
import species.Species;
import universe.StarSystem;

public class Ship {
	
	private int RenderRadius;
	private int size;
	private int speed;

	private double x;
	private double y;
	private double entityScale;
	private double scale;
	
	private BufferedImage shipImage;
	
	private ArrayList<Action> actionQueue;
	
	private StarSystem currentSystem;
	private Species species;
	
	public Ship(Species s) {
		
		x = 50;
		y = 50;
		scale = 0;
		RenderRadius = 5;
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
	
	public boolean clicked(int mouseX, int mouseY, double screenX, double screenY) {
		
		if(
				((mouseX > getX()+Main.WIDTH/2 - RenderRadius + screenX) && 
						(mouseX < getX()+Main.WIDTH/2 + RenderRadius + screenX)) && 
				((mouseY > getY()+Main.HEIGHT/2 - RenderRadius + screenY) && 
						(mouseY < getY()+Main.HEIGHT/2 + RenderRadius + screenY))
			) return true;
		
		return false;
		
	}
	
	public void render(Graphics g, double screenX, double screenY, double s) {
		
		if(scale != s) {
			scale = s;
			
			RenderRadius = (int)Math.round((size*entityScale*scale)/(100.0));
			RenderRadius = (RenderRadius < 5) ? 5: RenderRadius;
			
			shipImage = Draw.circle(RenderRadius, Color.WHITE.getRGB(), true);
		}
		
		int imageX = (int)Math.round((getX()+screenX) - shipImage.getWidth()/2 + Main.WIDTH/2);
		int imageY = (int)Math.round((getY()+screenY) - shipImage.getHeight()/2 + Main.HEIGHT/2);
		
		g.drawImage(shipImage, imageX, imageY, null);
		
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
