package ui.map;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import bodys.Body;
import ui.engine.Circle;
import ui.engine.ScreenPosition;
import ui.engine.Vector;
import ui.engine.VectorGraphics;
import ui.engine.VectorParser;
import universe.StarSystem;

public class StarSystemUi implements Chart {
	
	List<Vector> bodys;
	
	private StarSystem starSystem;
	
	public StarSystemUi(StarSystem ss) {
		starSystem = ss;
		Body s = starSystem.getBodys();
		bodys = getRenderPropertys(s);
	}

	@Override
	public void render(VectorGraphics g) {
		
		g.translationSet(ScreenPosition.CENTER);
		((Circle) bodys.get(0)).setRadius(50);
		g.draw(bodys.get(0));
		
	}
	
	private List<Vector> getRenderPropertys(Body s) {
		return VectorParser.getVectors("gfx\\body\\" + s.getType() + ".txt");
	}

}