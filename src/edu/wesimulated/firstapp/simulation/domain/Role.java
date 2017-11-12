package edu.wesimulated.firstapp.simulation.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.wesimulated.firstapp.model.RoleData;
import edu.wesimulated.firstapp.model.SimulationEntity;
import edu.wesimulated.firstapp.model.TaskNeed;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

public class Role implements NumericallyModeledEntity, Populatable {

	private Collection<TaskNeed> taskNeedsThatCanBeMet;
	private Collection<Assignment> assignments;
	private String name;

	@Override
	public Map<Characteristic, EntryValue> extractValues() {
		Map<Characteristic, EntryValue> values = new HashMap<>();
		values.put(RoleCharacteristic.AMOUNT_TASK_NEEDS, new EntryValue(Type.LONG, this.taskNeedsThatCanBeMet.size()));
		return values;
	}

	public Collection<TaskNeed> getTaskNeedsThatCanBeMet() {
		return taskNeedsThatCanBeMet;
	}

	@Override
	public String getIdentifier() {
		return this.getName();
	}

	@Override
	public IdentifiableType getType() {
		return IdentifiableType.ROLE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void populateFrom(SimulationEntity simulationEntity, SimulatorFactory factory) {
		RoleData roleData = (RoleData) simulationEntity;
		this.setName(roleData.getName());
	}

	public void addAssignment(Assignment newAssignment) {
		this.getAssignments().add(newAssignment);
	}

	public Collection<Assignment> getAssignments() {
		if (this.assignments == null) {
			this.assignments = new ArrayList<>();
		}
		return assignments;
	}

	public void setAssignments(Collection<Assignment> assignments) {
		this.assignments = assignments;
	}
}
