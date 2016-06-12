package edu.wesimulated.firstapp.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonData {

	private StringProperty firstName;
	private StringProperty lastName;
	private IntegerProperty hoursPerDay;
	// [Unit] unit of work per hour
	private FloatProperty efficiency;
	private ObservableList<RoleData> roles;

	public PersonData() {
		this(null, null);
	}

	public PersonData(String firstName, String lastName) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.hoursPerDay = new SimpleIntegerProperty(8);
		this.efficiency = new SimpleFloatProperty(0.8f);
		this.roles = FXCollections.observableArrayList();
	}

	public float calculateEffectiveMillisecondsPerDay() {
		return this.getHoursPerDay() * this.getEfficiency();
	}

	public String getFirstName() {
		return firstName.get();
	}

	public StringProperty firstNameProperty() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public String getLastName() {
		return lastName.get();
	}

	public StringProperty lastNameProperty() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public Integer getHoursPerDay() {
		return hoursPerDay.get();
	}

	public IntegerProperty hoursPerDayProperty() {
		return hoursPerDay;
	}

	public void setHoursPerDay(Integer hoursPerDay) {
		this.hoursPerDay.set(hoursPerDay);
	}

	public Float getEfficiency() {
		return efficiency.get();
	}

	public ObservableList<RoleData> getRoles() {
		return roles;
	}

	public void setRoles(ObservableList<RoleData> roles) {
		this.roles = roles;
	}

	public FloatProperty efficiencyProperty() {
		return efficiency;
	}

	public void setEfficiency(Float efficiency) {
		this.efficiency.set(efficiency);
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName.get() + ", lastName=" + lastName.get() + "]";
	}

	public void addRole(RoleData role) {
		this.roles.add(role);
	}
}

