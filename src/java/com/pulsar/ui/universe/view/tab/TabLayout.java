package ui.universe.view.tab;

import java.awt.Graphics;
import java.util.ArrayList;

import input.Keyboard;
import input.Mouse;

public class TabLayout {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private int tabWidth;
	private int selectedIndex;
	
	private boolean up;
	
	private ArrayList<Tab> tabList;
	
	public TabLayout(int x1, int y1, int maxX, boolean u) {
		
		height = 50;
		selectedIndex = 0;
		tabWidth = 0;
		
		up = u;
		width = maxX;
		x = x1;
		y = y1 - height;
		
		tabList = new ArrayList<Tab>();
		
	}
	
	public void addTab(Tab t) {
		
		t.setOriantation(up);
		
		if(tabList.isEmpty()) {
			t.setFirst(true);
			t.setSelected(true);
		}
		
		tabList.add(t);
		tabWidth += t.getWidth();
		
	}
	/**
	 * gets the spesific tap clicked on
	 */
	public boolean action(Mouse m, Keyboard k) {
		
		if(!m.buttonDown(1))
			return false;

		int x1 = (int)Math.round(m.getPosition().getX());
		int y1 = (int)Math.round(m.getPosition().getY());
		
		if(up)
			y1 += height;
		
		if(x1 > 0 && x1 < width) {
			if(y1 > 0 && y1 < height) {
				//loops through all rejestered tabs
				for(Tab t: tabList) {
					
					//did the tab get clicked
					if(t.clicked(x1, y1)) {
						
						tabList.get(selectedIndex).setSelected(false); //unselect current selected tab, relevent for lines
						t.setSelected(true); //select current tab
						selectedIndex = tabList.indexOf(t); //set index
						
						return true;
						
					}
					
					x1 -= t.getWidth(); //move x of corrnor
					
				}
			}
		}
		
		return false;
	}
	
	public int getSelected() {
		return selectedIndex;
	}
	
	public Tab getTab(int index) {
		return tabList.get(index);
	}

	/**
	 * loops through all regesterd taps and draws them
	 */
	public int render(Graphics g) {
		return render(g, 0, 0);
	}

	public int render(Graphics g, int x1, int y1) {
		
		int x2 = x+x1+tabWidth;
		int y2 = y+y1;
		
		for(int i = tabList.size()-1; i >= 0; i--) {
			Tab t = tabList.get(i);
			x2 = t.render(g, x2, y2);
		}
		
		return x2+tabWidth;
		
	}

	/**
	 * renders the view of the selected tab
	 */
	public void renderSelected(Graphics g, int x, int y) {
		
		if(tabList.isEmpty())
			return;
		
		tabList.get(selectedIndex).renderPane(g, x, y);
		
	}

	public boolean selectedAction(Mouse m, Keyboard k) {
		return tabList.get(selectedIndex).paneAction(m, k);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
