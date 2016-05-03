package edu.wesimulated.firstapp.model;

import java.util.List;

public class RamRow {

	private List<ResponsibilityAssignmentData> columns;
	private TaskData task;

	public RamRow() {
	}

	public List<ResponsibilityAssignmentData> getColumns() {
		return columns;
	}

	public void setColumns(List<ResponsibilityAssignmentData> columns) {
		this.columns = columns;
	}

	public TaskData getTask() {
		return task;
	}

	public void setTask(TaskData task) {
		this.task = task;
	}

	public ResponsibilityAssignmentData findByRole(RoleData role) {
		for (ResponsibilityAssignmentData column : columns) {
			if (column.getRole().equals(role)) {
				return column;
			}
		}
		return null;
	}
}
