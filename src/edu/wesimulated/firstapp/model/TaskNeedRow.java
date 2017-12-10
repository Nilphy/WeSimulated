package edu.wesimulated.firstapp.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class TaskNeedRow {

	private TaskNeedData taskNeedData;
	private BooleanProperty isSelected;

	public TaskNeedRow() {
		this.isSelected = new SimpleBooleanProperty(false);
	}

	public TaskNeedRow(TaskNeedType taskNeedType) {
		this();
		this.taskNeedData = new TaskNeedData(taskNeedType);
	}

	public TaskNeedType getTaskNeedType() {
		return this.taskNeedData.getTaskNeedType();
	}

	public Integer getUnitsOfWork() {
		return this.taskNeedData.getUnitsOfWork();
	}

	public IntegerProperty unitsOfWorkProperty() {
		return this.taskNeedData.unitsOfWorkProperty();
	}

	public void setUnitsOfWork(Integer unitsOfWork) {
		this.taskNeedData.unitsOfWorkProperty().set(unitsOfWork);
	}

	public BooleanProperty isSelectedProperty() {
		return this.isSelected;
	}

	public void setTaskNeedData(TaskNeedData taskNeedData) {
		this.taskNeedData = taskNeedData;
	}

	public TaskNeedData getTaskNeedData() {
		return this.taskNeedData;
	}
}
