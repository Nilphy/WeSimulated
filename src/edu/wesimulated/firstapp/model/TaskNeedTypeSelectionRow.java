package edu.wesimulated.firstapp.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class TaskNeedTypeSelectionRow {

	private TaskNeedType taskNeedType;
	private BooleanProperty isSelectedProperty;

	public TaskNeedTypeSelectionRow() {
		this.isSelectedProperty = new SimpleBooleanProperty();
	}

	public TaskNeedType getTaskNeedType() {
		return this.taskNeedType;
	}

	public void setTaskNeedType(TaskNeedType taskNeedType) {
		this.taskNeedType = taskNeedType;
	}

	public BooleanProperty isSelectedProperty() {
		return this.isSelectedProperty;
	}
}
