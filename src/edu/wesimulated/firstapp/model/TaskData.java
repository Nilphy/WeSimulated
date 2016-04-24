package edu.wesimulated.firstapp.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import edu.wesimulated.firstapp.simulation.domain.UnitsOfWorkInterpreter;

public class TaskData {

	private StringProperty name;
	private IntegerProperty unitsOfWork;
	private IntegerProperty id;
	public static int MAX_ID = 0;

	public TaskData() {
		this(null, null, null);
	}

	public TaskData(String name, Integer unitsOfWork, Integer id) {
		this.name = new SimpleStringProperty(name);
		this.unitsOfWork = new SimpleIntegerProperty(unitsOfWork == null ? 0 : unitsOfWork);
		this.id = new SimpleIntegerProperty(id == null ? 0 : id);
	}

	public float calculateEffortInMilliseconds() {
		return UnitsOfWorkInterpreter.uowToMilis(this.getUnitsOfWork());
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

	public Integer getId() {
		return this.id.get();
	}

	public void setId(Integer id) {
		this.id.set(id);
	}

	@Override
	public String toString() {
		return "Task [name=" + name.get() + ", id=" + id.get() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		TaskData theOther = (TaskData) obj;
		return theOther.getId().equals(this.getId());
	}

	public void assingId() {
		this.id.set(getNextId());
	}

	public synchronized int getNextId() {
		return ++MAX_ID;
	}
}
