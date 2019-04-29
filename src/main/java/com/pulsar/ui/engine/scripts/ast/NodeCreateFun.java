package ui.engine.scripts.ast;


public class NodeCreateFun implements NodeCreate {
	
	public final String name;
	public final NodeCallVar[] paramaters;
	public final Node[] body;
	
	public NodeCreateFun(String n, NodeList p, NodeList b) {
		name = n;
		
		paramaters = new NodeCallVar[p.nodeList.size()];
		for(int i = 0; i < p.nodeList.size(); i++) {
			paramaters[i] = (NodeCallVar) p.nodeList.get(i);
		}
		
		body = new Node[b.nodeList.size()];
		b.nodeList.toArray(body);
		
	}
	
}
