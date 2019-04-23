package ui.engine.scripts;

import java.util.ArrayList;
import java.util.List;

public class Function {
	
	private List<String> pars;

	public Function(List<String> p) {
		pars = new ArrayList<String>(p);
	}
	
	public void call(Object... objects) {
		System.out.println(objects);
	}
	
}
