package edu.wesimulated.firstapp.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.wesimulated.firstapp.model.TaskNeedType;

public interface TaskNeedHolder {

	public void addSelectedTaskNeeds(List<TaskNeedType> taskNeedTypesSelected);

	default List<TaskNeedType> filterTaskNeedsWithAlreadySelected(Collection<TaskNeedType> taskNeedTypesAlreadySelected) {
		List<TaskNeedType> filteredTasks = new ArrayList<>();
		for (TaskNeedType taskNeedType : TaskNeedType.values()) {
			if (!taskNeedTypesAlreadySelected.contains(taskNeedType)) {
				filteredTasks.add(taskNeedType);
			}
		}
		return filteredTasks;
	}
}
