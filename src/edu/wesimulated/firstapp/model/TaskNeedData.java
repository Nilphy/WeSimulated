package edu.wesimulated.firstapp.model;

import javafx.beans.property.IntegerProperty;

public class TaskNeedData {

	private TaskNeedType taskNeedType;
	private IntegerProperty unitsOfWork;

	public TaskNeedData() {
	}

	public TaskNeedData(TaskNeedType type) {
		this();
		this.taskNeedType = type;
	}

	public TaskNeedType getTaskNeedType() {
		return this.taskNeedType;
	}

	public void setTaskNeed(TaskNeedType taskNeedType) {
		this.taskNeedType = taskNeedType;
	}

	public Integer getUnitsOfWork() {
		return this.unitsOfWork.get();
	}

	public IntegerProperty unitsOfWorkProperty() {
		return this.unitsOfWork;
	}

	public void setUnitsOfWork(Integer unitsOfWork) {
		this.unitsOfWork.set(unitsOfWork);
	}
}
