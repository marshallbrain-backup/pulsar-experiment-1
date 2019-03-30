package ui.universe_ui;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import bodys.Body;
import ui.engine.ScreenPosition;
import ui.engine.VectorGraphics;
import ui.engine.VectorParser;
import universe.StarSystem;

public class StarSystemUi {
	
	private StarSystem starSystem;
	
	public StarSystemUi(StarSystem ss) {
		starSystem = ss;
		Body s = starSystem.getBodys();
		List<Map<String, String>> o = getRenderPropertys(s);
	}
	
	public void render(VectorGraphics g) {
		
		g.translationSet(ScreenPosition.CENTER);
		g.getGraphics().setColor(Color.WHITE);
		
	}

	private List<Map<String, String>> getRenderPropertys(Body s) {
		VectorParser.readVectorFile("gfx\\body\\" + s.getType() + ".txt");
		return null;
	}

}