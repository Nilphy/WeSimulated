package edu.wesimulated.firstapp.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import edu.wesimulated.firstapp.persistence.LocalDateAdapter;
import edu.wesimulated.firstapp.simulation.domain.UnitsOfWorkInterpreter;

public class TaskData {

	public static int MAX_ID = 0;

	private StringProperty name;
	private IntegerProperty unitsOfWork;
	private LocalDate startDate;
	private LocalDate endDate;
	private ObservableList<PersonData> responsiblePeople;
	private ObservableList<PersonData> accountablePeople;
	private ObservableList<PersonData> consultedPeople;
	private ObservableList<PersonData> informedPeople;
	private IntegerProperty id;
	private ObservableList<TaskDependencyData> taskDependencies;

	public TaskData() {
		this(null, null, null);
	}

	public TaskData(String name, Integer unitsOfWork, Integer id) {
		this.name = new SimpleStringProperty(name);
		this.unitsOfWork = new SimpleIntegerProperty(unitsOfWork == null ? 0 : unitsOfWork);
		this.id = new SimpleIntegerProperty(id == null ? 0 : id);
		this.taskDependencies = FXCollections.observableArrayList();
		this.resetAllPeopleAssignations();
	}

	public float calculateEffortInMilliseconds() {
		return UnitsOfWorkInterpreter.uowToMilis(this.getUnitsOfWork());
	}

	public void resetAllPeopleAssignations() {
		this.responsiblePeople = FXCollections.observableArrayList();
		this.accountablePeople = FXCollections.observableArrayList();
		this.consultedPeople = FXCollections.observableArrayList();
		this.informedPeople = FXCollections.observableArrayList();
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

	public ObservableList<TaskDependencyData> getTaskDependencies() {
		return taskDependencies;
	}

	public void setTaskDependencies(ObservableList<TaskDependencyData> taskDependencies) {
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

	public ObservableList<PersonData> getResponsiblePeople() {
		return responsiblePeople;
	}

	public void setResponsiblePeople(ObservableList<PersonData> responsiblePeople) {
		this.responsiblePeople = responsiblePeople;
	}

	public ObservableList<PersonData> getAccountablePeople() {
		return accountablePeople;
	}

	public void setAccountablePeople(ObservableList<PersonData> accountablePeople) {
		this.accountablePeople = accountablePeople;
	}

	public ObservableList<PersonData> getConsultedPeople() {
		return consultedPeople;
	}

	public void setConsultedPeople(ObservableList<PersonData> consultedPeople) {
		this.consultedPeople = consultedPeople;
	}

	public ObservableList<PersonData> getInformedPeople() {
		return informedPeople;
	}

	public void setInformedPeople(ObservableList<PersonData> informedPeople) {
		this.informedPeople = informedPeople;
	}

}
