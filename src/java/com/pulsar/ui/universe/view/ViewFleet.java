package ui.universe.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import bodys.Body;
import fleets.Action;
import fleets.Ship;
import input.Keyboard;
import input.Mouse;
import ui.universe.view.button.Button;
import ui.universe.view.pane.fleet.ActionUI;
import ui.universe.view.pane.list.ListAction;

public class ViewFleet extends View {
	
	private int width;
	private int height;
	
	private BufferedImage window;
	private BufferedImage closeImage;
	private BufferedImage entry;
	
	private Button closeButton;
	private ActionUI bodys;
	private Ship ship;
	
	public ViewFleet(Ship s) {
		
		init(s);
		
		bodys = new ActionUI(s.getSystem().getPlanets(), ship);
		
	}
	
	private void init(Ship s) {
		
		x = 6;
		y = 60;
		
		ship = s;
		
		try {
			window = ImageIO.read(new File("gfx\\ui\\view\\ship\\managment\\window.png"));
			closeImage = ImageIO.read(new File("gfx\\ui\\view\\button1.png"));
			entry = ImageIO.read(new File("gfx\\ui\\view\\ship\\managment\\action.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		width = window.getWidth();
		height = window.getHeight();
		
		closeButton = new Button(closeImage, "X", width-45, 10);
		
	}
	
	public boolean action(Mouse mo, Keyboard k) {
		
		if(!mo.buttonDown(1))
			return false;
		
		Mouse m = new Mouse(mo, x, y);
		
		int x = (int)Math.round(m.getPosition().getX());
		int y = (int)Math.round(m.getPosition().getY());
		
		if(0 < x && x < width) {
			if(0 < y && y < height) {
				if(closeButton.action(m, k)) {
					close = true;
					return true;
				} else {
					bodys.action(k, new Mouse(m, 5, bodys.getHeight()+10));
				}
				return true;
			}
		}
		
		return false;
		
	}
	
	public void render(Graphics g) {
		
		g.translate(x, y);
		
		g.drawImage(window, 0, 0, null);
		bodys.render(g, 5, bodys.getHeight()+10);
		
		int x1 = entry.getWidth()+10;
		int y1 = 5;
		g.drawImage(entry, x1, y1, null);
		if(ship.getActionList() != null) {
			x1 += 5;
			y1 += 5;
			for(Action a: ship.getActionList()) {
				ListAction la = new ListAction(a);
				if(la != null) {
					la.render(g, x1, y1);
					y1 += la.getHeight()+5;
				}
			}
		}
		
		closeButton.render(g);
		
		g.translate(-x, -y);
		
	}

}
