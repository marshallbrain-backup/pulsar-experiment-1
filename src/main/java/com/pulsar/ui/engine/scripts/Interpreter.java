package ui.engine.scripts;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ui.engine.scripts.ast.Node;
import ui.engine.scripts.ast.NodeCall;
import ui.engine.scripts.ast.NodeCallFun;
import ui.engine.scripts.ast.NodeCallVar;
import ui.engine.scripts.ast.NodeCreateFun;
import ui.engine.scripts.ast.NodeLitteral;

public class Interpreter {
	
	private static Engine engine;
	
	public static void initiateInterpreter(Engine e) {
		engine = e;
	}
	
	public static void runFunction(NodeCreateFun f, Object[] objects) {
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("engine", engine);
		
		for(int i = 0; i < f.paramaters.length; i++) {
			variables.put(f.paramaters[i].name, objects[i]);
		}
		
		for(Node l: f.body) {
			runLine(l, new HashMap<String, Object>(variables));
		}
		
	}
	
	private static Object runLine(Node l, Map<String, Object> v) {
		
		if(l instanceof NodeCall) {
			return runCall((NodeCall) l, v);
		} else if(l instanceof NodeLitteral) {
			return getLitteral((NodeLitteral) l);
		}
		
		return null;
		
	}
	
	private static Object getLitteral(NodeLitteral l) {
		return l.getValue();
	}

	private static Object runCall(NodeCall l, Map<String, Object> v) {
		
		if(l instanceof NodeCallVar) {
			
			NodeCallVar c = (NodeCallVar) l;
			
			if(c.parent == null) {
				return v.get(c.name);
			}
			
			try {
				return runLine(c.parent, v).getClass().getField(c.name);
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
			
		} else if(l instanceof NodeCallFun) {
			
			NodeCallFun c = (NodeCallFun) l;
			
			Object[] parList = new Object[c.paramaters.length];
			for(int i = 0; i < parList.length; i++) {
				parList[i] = runLine(c.paramaters[i], v);
			}
			
			Class<?>[] classList = new Class[c.paramaters.length];
			for(int i = 0; i < parList.length; i++) {
				classList[i] = parList[i].getClass();
			}
			
			Object fun = runLine(c.parent, v);
			Method[] methods = fun.getClass().getMethods();
			for(Method m: methods) {
				if(c.name.equals(m.getName())) {
					Class<?>[] cl = m.getParameterTypes();
					try {
						Method method = fun.getClass().getMethod(c.name, cl);
						return method.invoke(fun, parList);
					} catch (NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		
		return null;
		
	}
	
}
