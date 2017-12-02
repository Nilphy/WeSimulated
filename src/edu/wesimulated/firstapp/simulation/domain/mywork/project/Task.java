package edu.wesimulated.firstapp.simulation.domain.mywork.project;

import java.util.Map.Entry;

import edu.wesimulated.firstapp.model.TaskNeedType;

public class Task extends edu.wesimulated.firstapp.simulation.domain.Task {

	public void extendDuration(double scale) {
		for (Entry<TaskNeedType, Number> entry : this.getCostInHoursPerTaskNeed().entrySet()) {
			Double actualCostInHours = entry.getValue().doubleValue();
			Double escalatedCostInHours = actualCostInHours + actualCostInHours * scale;
			entry.setValue(escalatedCostInHours);
		}
	}
}
