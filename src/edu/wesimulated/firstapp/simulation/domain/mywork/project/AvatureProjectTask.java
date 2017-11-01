package edu.wesimulated.firstapp.simulation.domain.mywork.project;

import java.util.Map.Entry;

import edu.wesimulated.firstapp.model.TaskNeed;
import edu.wesimulated.firstapp.simulation.domain.Task;

public class AvatureProjectTask extends Task {

	public void extendDuration(double scale) {
		for (Entry<TaskNeed, Number> entry : this.getCostInHoursPerTaskNeed().entrySet()) {
			Double actualCostInHours = entry.getValue().doubleValue();
			Double escalatedCostInHours = actualCostInHours + actualCostInHours * scale;
			entry.setValue(escalatedCostInHours);
		}
	}
}
