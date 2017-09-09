package edu.wesimulated.firstapp.simulation.domain;

import java.util.Collection;
import java.util.Map;

import edu.wesimulated.firstapp.model.TaskNeed;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

public class Role implements NumericallyModeledEntity {

	private Collection<TaskNeed> taskNeedsThatCanBeMet;

	@Override
	public Map<Characteristic, EntryValue> extractValues() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<TaskNeed> getTaskNeedsThatCanBeMet() {
		return taskNeedsThatCanBeMet;
	}
}
