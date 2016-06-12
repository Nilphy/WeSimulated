package edu.wesimulated.firstapp.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ResponsibilityAssignmentData {

	private RoleData role;
	private TaskData task;
	private BooleanProperty responsible;
	private BooleanProperty accountable;
	private BooleanProperty consulted;
	private BooleanProperty informed;

	public ResponsibilityAssignmentData() {
		this.responsible = new SimpleBooleanProperty(false);
		this.accountable = new SimpleBooleanProperty(false);
		this.consulted = new SimpleBooleanProperty(false);
		this.informed = new SimpleBooleanProperty(false);
	}

	public boolean isOfRaciType(RaciType raciType) {
		switch (raciType) {
		case Responsible:
			return this.isResponsible();
		case Accountable:
			return this.isAccountable();
		case Consulted:
			return this.isConsulted();
		case Informed:
			return this.isInformed();
		default:
			return false;
		}
	}

	public RoleData getRole() {
		return this.role;
	}

	public void setRole(RoleData role) {
		this.role = role;
	}

	public TaskData getTask() {
		return this.task;
	}

	public void setTask(TaskData task) {
		this.task = task;
	}

	public void setResponsible(Boolean responsible) {
		this.responsible.set(responsible);
	}

	public BooleanProperty responsibleProperty() {
		return this.responsible;
	}

	public Boolean isResponsible() {
		return this.responsible.get();
	}

	public void setAccountable(Boolean accountable) {
		this.accountable.set(accountable);
	}

	public BooleanProperty accountableProperty() {
		return this.accountable;
	}

	public Boolean isAccountable() {
		return this.accountable.get();
	}

	public void setConsulted(Boolean consulted) {
		this.consulted.set(consulted);
	}

	public BooleanProperty consultedProperty() {
		return this.consulted;
	}

	public Boolean isConsulted() {
		return this.consulted.get();
	}

	public void setInformed(Boolean informed) {
		this.informed.set(informed);
	}

	public BooleanProperty informedProperty() {
		return informed;
	}

	public Boolean isInformed() {
		return this.informed.get();
	}
}
