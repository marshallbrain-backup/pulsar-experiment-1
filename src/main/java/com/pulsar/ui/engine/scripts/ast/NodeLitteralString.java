package ui.engine.scripts.ast;


public class NodeLitteralString implements NodeLitteral {
	
	public final String value;
	
	public NodeLitteralString(String v) {
		value = v;
	}

	@Override
	public Object getValue() {
		return value;
	}
	
}
