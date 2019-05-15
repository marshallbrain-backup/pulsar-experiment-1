package math;

public class UnitConverter {
	
	public static Unit convert(Unit i, UnitType from, UnitType to) {
		Unit m = convertToMeter(i, from);
		Unit p = convertFromMeter(m, to);
		return p;
		
	}
	
	private static Unit convertToMeter(Unit i, UnitType from) {
		if(from == UnitType.METER)
			return i;
		
		return null;
	}
	
	private static Unit convertFromMeter(Unit i, UnitType to) {
		if(to == UnitType.METER)
			return i;
		
		return null;
	}

}
