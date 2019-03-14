package ui.universe.view.pane.fleet;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import bodys.Body;
import bodys.Planet;
import fleets.Ship;
import fleets.Action;
import input.Keyboard;
import input.Mouse;
import ui.universe.view.pane.list.ListAction;
import ui.universe.view.pane.list.ListBody;

public class ActionUI {
	
	private BufferedImage entry;
	
	private ArrayList<ListBody> bodyList;
	private ArrayList<ListAction> actionList;
	
	private Body selectedBody;
	private Ship ship;
	
	public ActionUI(ArrayList<Planet> p, Ship s) {
		
		ship = s;
		
		bodyList = new ArrayList<ListBody>();
		
		for(Body b: p) {
			bodyList.add(new ListBody(b));
		}
		
		try {
			entry = ImageIO.read(new File("gfx\\ui\\view\\ship\\managment\\action.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public boolean action(Keyboard k, Mouse m) {
		
		if(!m.buttonDownOnce(1))
			return false;
		
		int x = (int)Math.round(m.getPosition().getX());
		int y = (int)Math.round(m.getPosition().getY());
		
		if(0 < x && x < getWidth()) {
			if(0 < y && y < getHeight()) {
				int i = y/(bodyList.get(0).getHeight()+5);
				if(x <= entry.getWidth() && i < bodyList.size()) {
					selectedBody = bodyList.get(i).action(k, new Mouse(m, 0, (bodyList.get(0).getHeight()+5)*i));
					if(selectedBody != null) {
						actionList = selectedBody.getActions();
					}
				} else if(actionList != null && i < actionList.size()) {
					Action selectedAction = actionList.get(i).action(k, new Mouse(m, entry.getWidth()+5, (bodyList.get(0).getHeight()+5)*i));
					if(selectedAction != null) {
						ship.addAction(selectedAction);
					}
				}
				return true;
			}
		}
		
		return false;
		
	}

	public void render(Graphics g, int x, int y) {
		
		g.drawImage(entry, x, y, null);
		g.drawImage(entry, x+entry.getWidth()+5, y, null);
		
		int x1 = x+5;
		int y1 = y+5;
		
		for(ListBody a: bodyList) {
			y1 = a.render(g, x1, y1);
		}
		
		x1 = x+entry.getWidth()+10;
		y1 = y+5;
		
		if(actionList != null) {
			for(ListAction a: actionList) {
				y1 = a.render(g, x1, y1);
			}
		}
		
	}
	
	public int getWidth() {
		return (entry.getWidth()*2)+5;
	}
	
	public int getHeight() {
		return entry.getHeight();
	}

}
