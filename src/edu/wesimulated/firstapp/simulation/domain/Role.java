package edu.wesimulated.firstapp.simulation.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.wesimulated.firstapp.model.TaskNeed;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

public class Role implements NumericallyModeledEntity {

	private Collection<TaskNeed> taskNeedsThatCanBeMet;

	@Override
	public Map<Characteristic, EntryValue> extractValues() {
		Map<Characteristic, EntryValue> values = new HashMap<>();
		values.put(RoleCharacteristic.AMOUNT_TASK_NEEDS, new EntryValue(Type.Long, this.taskNeedsThatCanBeMet.size()));
		return values;
	}

	public Collection<TaskNeed> getTaskNeedsThatCanBeMet() {
		return taskNeedsThatCanBeMet;
	}
}
