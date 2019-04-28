package ui.engine.scripts.ast;


public class NodeCreateFun implements NodeCreate {
	
	public final String name;
	public final Node[] paramaters;
	public final Node[] body;
	
	public NodeCreateFun(String n, NodeList p, NodeList b) {
		name = n;
		
		paramaters = new Node[p.nodeList.size()];
		p.nodeList.toArray(paramaters);
		
		body = new Node[b.nodeList.size()];
		b.nodeList.toArray(body);
		
	}
	
}
