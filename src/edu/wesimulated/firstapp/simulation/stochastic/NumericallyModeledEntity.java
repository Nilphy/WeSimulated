package edu.wesimulated.firstapp.simulation.stochastic;

import java.util.Map;

import edu.wesimulated.firstapp.simulation.domain.Characteristic;
public interface NumericallyModeledEntity {

	public Map<Characteristic, EntryValue> extractValues();
	
}
