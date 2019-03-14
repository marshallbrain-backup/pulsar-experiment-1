package bodys;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Pulsar.Main;
import files.type.TypePlanet;
import gfx.Draw;
import universe.StarSystem;

public class Planet extends Body {
	
//	private int orbitCenterX;
//	private int orbitCenterY;
//	private int radius;
	private int size;
	private int color;
	
	private double entityScale;
	
	private BufferedImage planet;
//	private BufferedImage orbit;
	private BufferedImage statis;

	/**
	 * initalize planet
	 * 
	 * @param typePlanet the planet type
	 * @param radius orbit radius of planet
	 */
	public Planet(TypePlanet tp, StarSystem s, int r) {
		super(s);
		
//		orbitCenterX = 0;
//		orbitCenterY = 0;
//		radius = r;
		scale = 0;
		RenderRadius = 0;
		statis = null;
		
		color = tp.getColor();
		size = tp.getSize();
		entityScale = tp.getEntityScale();
		type = tp.getName();
		colonyType = tp.colonyType();
		colonizable = tp.isColonizable();
		
		int a = random.nextInt(360);
		
		x = (int)Math.round(r * Math.cos(Math.toRadians(a)));
		y = (int)Math.round(r * Math.sin(Math.toRadians(a)));
		
	}

	/**
	 * generates planets
	 * 
	 * @param graphicts graghicts for the colony
	 * @param screenX x position of the screen
	 * @param screenY y position of the screen
	 * @param scale screen scale
	 */
	public void render(Graphics g, double screenX, double screenY, double s) {
		
		//get rendered radeus based on scale
		if(scale != s || (statis == null && colony != null)) {
			scale = s;
			
			RenderRadius = (int)Math.round((size*entityScale*scale)/(100.0));
			RenderRadius = (RenderRadius < 8) ? 8: RenderRadius;
//			RenderRadius = (RenderRadius > 100) ? 100: RenderRadius;
			
			planet = Draw.circle(RenderRadius, color, true);
//			orbit = Draw.circle((int)Math.round(radius*scale), 0, false);
			if(colony != null)
				statis = Draw.circle(RenderRadius, 0, 1, false);
			
		}
		
		//get position of planet
		int x1 = (int)Math.round((getX()+screenX) - planet.getWidth()/2 + Main.WIDTH/2);
		int y1 = (int)Math.round((getY()+screenY) - planet.getHeight()/2 + Main.HEIGHT/2);
		
//		int x2 = (int)Math.round((screenX + orbitCenterX) - orbit.getWidth()/2 + Main.WIDTH/2);
//		int y2 = (int)Math.round((screenY + orbitCenterY) - orbit.getHeight()/2 + Main.HEIGHT/2);
		
//		g.drawImage(orbit, x2, y2, null);
		g.drawImage(planet, x1, y1, null);
//		g.setColor(Color.BLUE);
//		g.fillRect(x1-2+planet.getWidth()/2, y1-2+planet.getHeight()/2, 4, 4);
		//draw ring if colony exist
		if(colony != null)
			g.drawImage(statis, x1-1, y1-1, null);
		
	}

}
