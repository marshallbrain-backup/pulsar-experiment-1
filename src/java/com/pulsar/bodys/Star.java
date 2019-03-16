package bodys;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import files.type.TypeStar;
import gfx.Draw;
import pulsar.Main;
import universe.StarSystem;

public class Star extends Body {
	
	private int size;
	private int color;
	
	private double entityScale;
	
	private String name;
	
	private BufferedImage star;

	/**
	 * initalize planet
	 * 
	 * @param typePlanet the planet type
	 */
	public Star(TypeStar sc, StarSystem s) {
		super(s);
		
		x = 0;
		y = 0;
		scale = 0;
		renderRadius = 0;
		
		name = sc.getName();
		color = sc.getColor();
		size = sc.getSize();
		entityScale = sc.getEntityScale();
		
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
		
		if(scale != s) {
			
			scale = s;
					
			renderRadius = (int)Math.round((size*entityScale*scale)/80.0);
			renderRadius = (renderRadius < 10) ? 10: renderRadius;
			
			star = Draw.circle(renderRadius, color, true);
			
		}//-offsetY*(scale-1)
		
		int imageX = (int)Math.round((getX()+screenX) - star.getWidth()/2 + Main.WIDTH/2);
		int imageY = (int)Math.round((getY()+screenY) - star.getHeight()/2 + Main.HEIGHT/2);
		
		g.drawImage(star, imageX, imageY, null);
		
	}
	
	public String toString() {
		return name;
	}

}
