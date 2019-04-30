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
	
	public static void runFunction(NodeCreateFun f, Object[] objects, Map<String, NodeCreateFun> functions) {
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("engine", engine);
		
		for(int i = 0; i < f.paramaters.length; i++) {
			variables.put(f.paramaters[i].name, objects[i]);
		}
		
		for(Node l: f.body) {
			runLine(l, new HashMap<String, Object>(variables), functions);
		}
		
	}
	
	private static Object runLine(Node l, Map<String, Object> v, Map<String, NodeCreateFun> f) {
		
		if(l instanceof NodeCall) {
			return runCall((NodeCall) l, v, f);
		} else if(l instanceof NodeLitteral) {
			return getLitteral((NodeLitteral) l);
		}
		
		return null;
		
	}
	
	private static Object getLitteral(NodeLitteral l) {
		return l.getValue();
	}

	private static Object runCall(NodeCall l, Map<String, Object> v, Map<String, NodeCreateFun> f) {
		
		if(l instanceof NodeCallVar) {
			
			NodeCallVar c = (NodeCallVar) l;
			
			if(c.parent == null) {
				return v.get(c.name);
			}
			
			try {
				return runLine(c.parent, v, f).getClass().getField(c.name);
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
			
		} else if(l instanceof NodeCallFun) {
			
			NodeCallFun c = (NodeCallFun) l;
			Object p = runLine(c.parent, v, f);
			
			Object[] parList = new Object[c.paramaters.length];
			

			for(int i = 0; i < parList.length; i++) {
				parList[i] = runLine(c.paramaters[i], v, f);
			}
			
			if(p == null) {
			} else {
				runCallFun(c, p, parList);
			}
			
//			Object[] parList = new Object[c.paramaters.length];
//			if(c.name.equals("openView")) {
//				parList = new Object[2];
//				Object[] list = new Object[c.paramaters.length-1];
//				for(int i = 0; i < list.length; i++) {
//					list[i] = runLine(c.paramaters[i+1], v);
//				}
//				parList[1] = list;
//			}
//			for(int i = 0; i < parList.length; i++) {
//				if(parList[i] == null) {
//					parList[i] = runLine(c.paramaters[i], v);
//				}
//			}
//			
//			Class<?>[] classList = new Class[c.paramaters.length];
//			for(int i = 0; i < parList.length; i++) {
//				classList[i] = parList[i].getClass();
//			}
//			
//			Object fun = runLine(c.parent, v, f);
//			try {
//				Method method = fun.getClass().getMethod(c.name, classList);
//				return method.invoke(fun, parList);
//			} catch (NoSuchMethodException | SecurityException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//				e.printStackTrace();
//			}
			
		}
		
		return null;
		
	}

	private static Object runCallFun(NodeCallFun l, Object p, Object[] par) {
		
		if(p instanceof Engine && l.name.equals("openView")) {
			Object name = par[0];
			Object[] a = new Object[par.length-1];
			for(int i = 0; i < a.length; i++) {
				a[i] = par[i+1];
			}
			par = new Object[2];
			par[0] = name;
			par[1] = a;
		}
		
		Class<?>[] classList = new Class[l.paramaters.length];
		for(int i = 0; i < par.length; i++) {
			classList[i] = par[i].getClass();
		}
		
		try {
			Method method = p.getClass().getMethod(l.name, classList);
			return method.invoke(p, par);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
