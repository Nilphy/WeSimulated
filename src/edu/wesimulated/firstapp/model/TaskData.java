package edu.wesimulated.firstapp.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import edu.wesimulated.firstapp.persistence.LocalDateAdapter;
import edu.wesimulated.firstapp.simulation.domain.UnitsOfWorkInterpreter;

public class TaskData {

	public static int MAX_ID = 0;

	private StringProperty name;
	private IntegerProperty unitsOfWork;
	private LocalDate startDate;
	private LocalDate endDate;
	private IntegerProperty id;
	private ObservableList<TaskDependency> taskDependencies;

	public TaskData() {
		this(null, null, null);
	}

	public TaskData(String name, Integer unitsOfWork, Integer id) {
		this.name = new SimpleStringProperty(name);
		this.unitsOfWork = new SimpleIntegerProperty(unitsOfWork == null ? 0 : unitsOfWork);
		this.id = new SimpleIntegerProperty(id == null ? 0 : id);
		this.taskDependencies = FXCollections.observableArrayList();
	}

	public float calculateEffortInMilliseconds() {
		return UnitsOfWorkInterpreter.uowToMilis(this.getUnitsOfWork());
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

	public void setId(Integer id) {
		this.id.set(id);
	}

	public Integer getId() {
		return this.id.get();
	}

	public ObservableList<TaskDependency> getTaskDependencies() {
		return taskDependencies;
	}

	public void setTaskDependencies(ObservableList<TaskDependency> taskDependencies) {
		this.taskDependencies = taskDependencies;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

}
