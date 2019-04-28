package ui.engine.scripts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ui.engine.scripts.ast.NodeCreateFun;

public class Interpreter {
	
	private static Engine engine;
	
	public static void initiateInterpreter(Engine e) {
		engine = e;
	}
	
	public static void runFunction(NodeCreateFun n, Object[] objects) {
		
		Map<String, Object> variables = new HashMap<String, Object>();
		
	}
	
}
