package edu.wesimulated.firstapp.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class TaskPeopleAssignmentRow {

	private RaciType raciType;
	private PersonData person;
	private BooleanProperty isSelectedProperty;

	public TaskPeopleAssignmentRow() {
		this.isSelectedProperty = new SimpleBooleanProperty(false);
	}

	public RaciType getRaciType() {
		return raciType;
	}

	public void setRaciType(RaciType raciType) {
		this.raciType = raciType;
	}

	public PersonData getPerson() {
		return person;
	}

	public void setPerson(PersonData person) {
		this.person = person;
	}

	public BooleanProperty isSelectedProperty() {
		return isSelectedProperty;
	}
}
