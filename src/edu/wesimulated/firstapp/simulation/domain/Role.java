package edu.wesimulated.firstapp.simulation.domain;

import java.util.Map;

import edu.wesimulated.firstapp.model.WorkType;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVariableName;

public class Role implements NumericallyModeledEntity {

	@Override
	public Map<StochasticVariableName, EntryValue> extractValues() {
		// TODO Auto-generated method stub
		return null;
	}

	public WorkType getWorkType() {
		// TODO Auto-generated method stub
		return null;
	}
}
