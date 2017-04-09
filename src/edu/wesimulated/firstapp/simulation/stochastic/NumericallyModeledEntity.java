package edu.wesimulated.firstapp.simulation.stochastic;

import java.util.Map;

public interface NumericallyModeledEntity {

	public Map<StochasticVariableName, EntryValue> extractValues();
}
