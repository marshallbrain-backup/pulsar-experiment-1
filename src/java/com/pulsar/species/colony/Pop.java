package species.colony;

import files.type.TypeCategory;

public class Pop {
	
	private TypeCategory category;
	
	public Pop(TypeCategory c) {
		category = c;
	}

	public boolean equals(Pop p) {
		return true;
	}

	public TypeCategory getRank() {
		return category;
	}
	
	public String toString() {
		return "pop";
	}
	
}
