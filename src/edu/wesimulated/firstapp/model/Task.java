package edu.wesimulated.firstapp.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {

	private StringProperty name;
	private IntegerProperty unitsOfWork;

	public Task() {
		this(null, null);
	}

	public Task(String name, Integer unitsOfWork) {
		this.name = new SimpleStringProperty(name);
		this.unitsOfWork = new SimpleIntegerProperty(unitsOfWork == null ? 0 : unitsOfWork);
		
	}

	public StringProperty nameProperty() {
		return name;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public IntegerProperty unitsOfWorkProperty() {
		return unitsOfWork;
	}

	public Integer getUnitsOfWork() {
		return unitsOfWork.get();
	}

	public void setUnitsOfWork(Integer unitsOfWork) {
		this.unitsOfWork.set(unitsOfWork);
	}
}
