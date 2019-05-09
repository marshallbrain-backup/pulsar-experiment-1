package ui.engine.scripts;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ui.engine.scripts.ast.Node;
import ui.engine.scripts.ast.NodeAssignVar;
import ui.engine.scripts.ast.NodeCall;
import ui.engine.scripts.ast.NodeCallFun;
import ui.engine.scripts.ast.NodeCallVar;
import ui.engine.scripts.ast.NodeCreate;
import ui.engine.scripts.ast.NodeCreateFun;
import ui.engine.scripts.ast.NodeCreateInstance;
import ui.engine.scripts.ast.NodeCreateVar;
import ui.engine.scripts.ast.NodeList;
import ui.engine.scripts.ast.NodeLitteral;
import ui.engine.scripts.ast.NodeStatement;
import ui.engine.scripts.ast.NodeStatementElse;
import ui.engine.scripts.ast.NodeStatementIf;
import ui.engine.vectors.VectorGroup;

public class Interpreter {
	
	private static Engine engine;
	
	public static void initiateInterpreter(Engine e) {
		engine = e;
	}
	
	public static void runFunction(NodeCreateFun f, VectorGroup vg, Object[] objects, Map<String, NodeCreateFun> functions) {
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("engine", engine);
		variables.put("vectorLayer", vg);
		
		for(int i = 0; i < f.paramaters.length; i++) {
			variables.put(f.paramaters[i].name, objects[i]);
		}
		
		runList(Arrays.asList(f.body), variables, functions);
		
	}
	
	private static void runList(List<Node> l, Map<String, Object> v, Map<String, NodeCreateFun> f) {
		
		
		for(Node n: l) {
			runLine(n, v, f);
		}
		
	}
	
	private static Object runLine(Node l, Map<String, Object> v, Map<String, NodeCreateFun> f) {
		
		if(l instanceof NodeAssignVar) {
			runAssignVar((NodeAssignVar) l, v, f);
		} else if(l instanceof NodeCall) {
			return runCall((NodeCall) l, v, f);
		} else if(l instanceof NodeLitteral) {
			return getLitteral((NodeLitteral) l);
		} else if(l instanceof NodeStatement) {
			runStatement((NodeStatement) l, v, f);
		} else if(l instanceof NodeCreateInstance) {
			return runCreateInstance((NodeCreateInstance) l);
		}
		
		return null;
		
	}
	
	private static void runStatement(NodeStatement l, Map<String, Object> v, Map<String, NodeCreateFun> f) {
		
		if(l instanceof NodeStatementIf) {
			
			NodeStatementIf ifState = (NodeStatementIf) l;
			
			Object condition = runLine(ifState.condition, v, f);
			
			if(getBoolean(condition)) {
				runList(((NodeList) ifState.code).nodeList, v, f);
			} else {
				runLine(ifState.elseCode, v, f);
			}
			
		} else if(l instanceof NodeStatementElse) {
			
			NodeStatementElse elseState = (NodeStatementElse) l;
			
			runList(((NodeList) elseState.code).nodeList, v, f);
			
		}
		
	}

	
	private static boolean getBoolean(Object condition) {
		
		if(condition != null) {
			return true;
		}
		
		return false;
	}

	private static Object runCreateInstance(NodeCreateInstance l) {
		
		NodeCallFun f = (NodeCallFun) l.name;
		NodeCallVar n = (NodeCallVar) f.parent;
		String path = f.name;
		
		while(n != null) {
			path = n.name + "." + path;
			n = (NodeCallVar) n.parent;
		}
		
		try {
			Class<?> clazz = Class.forName("ui.engine." + path);
			Constructor<?> ctor = clazz.getConstructor();
			return ctor.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	private static void runAssignVar(NodeAssignVar l, Map<String, Object> v, Map<String, NodeCreateFun> f) {
		
		if(l.target instanceof NodeCreate) {
			runCreate((NodeCreate) l.target, v);
		}
		
		Object var = runLine(l.value, v, f);
		v.put(l.name, var);
		
	}
	
	private static void runCreate(NodeCreate l, Map<String, Object> v) {
		
		if(l instanceof NodeCreateVar) {
			v.put(((NodeCreateVar) l).name, null);
		}
		
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
				return runCallFun(c, p, parList);
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
			Object o = method.invoke(p, par);
			return o;
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
