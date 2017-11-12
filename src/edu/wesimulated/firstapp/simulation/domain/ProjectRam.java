package edu.wesimulated.firstapp.simulation.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

public class ProjectRam implements NumericallyModeledEntity {

	private Collection<Assignment> assignments;

	@Override
	public Map<Characteristic, EntryValue> extractValues() {
		// TODO complete ProjectRam.extractValues
		return null;
	}

	public Collection<Assignment> getAssignments() {
		if (this.assignments == null) {
			this.setAssignments(new ArrayList<>());
		}
		return assignments;
	}

	private void setAssignments(Collection<Assignment> assignments) {
		this.assignments = assignments;
	}

	public void addAssignment(Assignment assignment) {
		this.getAssignments().add(assignment);
	}
}
