package ui.universe.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import bodys.Body;
import input.Keyboard;
import input.Mouse;
import ui.universe.view.button.Button;
import ui.universe.view.pane.Pane;
import ui.universe.view.pane.detail.Detail;
import ui.universe.view.tab.Tab;
import ui.universe.view.tab.TabLayout;

public class ViewBody extends View {
	
	private int width = 924;
	private int height = 756;
	
	private BufferedImage line;
	private BufferedImage background;
	private BufferedImage closeImage;
	
	private TabLayout tabs;
	private Button closeButton;
	private Detail detail;
	private Body body;
	
	public ViewBody(Body b) {
		
		body = b;
		
		init(body);
		createTabs(body);
		
	}
	
	private void createTabs(Body b) {
		
		ArrayList<Pane> pList = b.getPanes(detail);
		
		for(Pane p: pList) {
			tabs.addTab(new Tab("Eden", p, detail));
		}
		
		tabs.getTab(0).setFirst(true);
		
	}
	
	private void init(Body b) {
		
		x = 100;
		y = 150;
		
		detail = new Detail(b, width, 0);
		tabs = new TabLayout(0, 0, width, true);
		
		try {
			line = ImageIO.read(new File("gfx\\ui\\view\\line.png"));
			background = ImageIO.read(new File("gfx\\ui\\view\\background.png"));
			closeImage = ImageIO.read(new File("gfx\\ui\\view\\button1.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		closeButton = new Button(closeImage, "X", width-44, 12);
		
	}
	
	public void reloadTabs() {
		init(body);
		createTabs(body);
	}
	
	public boolean action(Mouse mo, Keyboard k) {
		
		Mouse m = new Mouse(mo, x, y);
		
		if(closeButton.action(m, k)) {
			close = true;
			return true;
		} else if(detail.action(m, k)) {
			if(body.shouldReload())
				reloadTabs();
			return true;
		} else if(tabs.action(m, k))
			return true;
		else if(tabs.selectedAction(m, k))
			return true;
		
		return false;
		
	}
	
	public void render(Graphics g) {
		
		g.translate(x, y);
		
		g.drawImage(background, 0, 0, width, height, null);
		g.drawImage(line, 0, 0, 4, height, null);
		g.drawImage(line, 0+width, 0, 4, height, null);
		
		int x1 = tabs.render(g);
		closeButton.render(g);
		
		g.drawImage(line, x1, 0, 0+width-x1, 4, null);
		
		tabs.renderSelected(g, 0, 0);
		detail.render(g);
		
		g.translate(-x, -y);
		
	}

}
