package species.colony;

import bodys.Body;
import species.Species;

public class Colony {
	
	private Body body;
	
	/**
	 * initalizes  colony
	 * 
	 * @param planet the planet that the colony is on
	 * @param species the species that the colony is under controle by, relevent for species boneses
	 * @param gamefile the gamefile containing district info
	 */
	public Colony(Body b, Species s) {
		
		body = b;
		
		body.setColony(this); 
		
	}

}
