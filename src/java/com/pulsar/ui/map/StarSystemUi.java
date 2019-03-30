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
	
	private StarSystem starSystem;
	
	public StarSystemUi(StarSystem ss) {
		starSystem = ss;
		Body s = starSystem.getBodys();
		List<Vector> o = getRenderPropertys(s);
	}

	@Override
	public void render(VectorGraphics g) {
		
		g.translationSet(ScreenPosition.CENTER);
		g.getGraphics().setColor(Color.WHITE);
		g.drawCircle(new Circle(0, 0, 50));
		
	}

	private List<Vector> getRenderPropertys(Body s) {
		return VectorParser.getVectors("gfx\\body\\" + s.getType() + ".txt");;
	}

}