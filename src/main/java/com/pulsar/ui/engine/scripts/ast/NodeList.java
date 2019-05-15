package ui.engine.scripts.ast;

import java.util.ArrayList;
import java.util.List;

public class NodeList implements Node {
	
	public final List<Node> nodeList;
	
	public NodeList(List<Node> l) {
		nodeList = new ArrayList<Node>(l);
	}
	
	public String toString() {
		return nodeList.toString();
	}
	
}
