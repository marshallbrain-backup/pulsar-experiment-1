package ui.engine.scripts.ast;


public class NodeLitteralInt implements NodeLitteral {
	
	public final int value;
	
	public NodeLitteralInt(String v) {
		value = Integer.parseInt(v);
	}

	@Override
	public Object getValue() {
		return value;
	}
	
}
