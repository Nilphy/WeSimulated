package edu.wesimulated.firstapp.model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import edu.wesimulated.firstapp.simulation.domain.UnitsOfWorkInterpreter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TaskData implements Observable {

	private StringProperty name;
	private IntegerProperty unitsOfWork;

	public TaskData() {
		this(null, null);
	}

	public TaskData(String name, Integer unitsOfWork) {
		this.name = new SimpleStringProperty(name);
		this.unitsOfWork = new SimpleIntegerProperty(unitsOfWork == null ? 0 : unitsOfWork);
		
	}

	public float calculateEffortInMilliseconds() {
		return UnitsOfWorkInterpreter.uowToMilis(this.getUnitsOfWork());
	}

	@Override
	public void addListener(InvalidationListener listener) {
		this.name.addListener(listener);
		this.unitsOfWork.addListener(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		this.name.removeListener(listener);
		this.unitsOfWork.removeListener(listener);
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

	@Override
	public String toString() {
		return "Task [name=" + name.get() + "]";
	}
}
