package edu.wesimulated.firstapp.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import edu.wesimulated.firstapp.simulation.SimulatorType;

public class PersonData implements SimulationEntity {

	private StringProperty firstName;
	private StringProperty lastName;
	private StringProperty id;
	private IntegerProperty hoursPerDay;
	// [Unit] unit of work per hour
	private FloatProperty efficiency; // TODO replace this only characteristic with the map of characteristics
	private ObservableList<RoleData> roles;

	public PersonData() {
		this(null, null, null);
	}

	public PersonData(String firstName, String lastName, String id) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.id = new SimpleStringProperty(id);
		this.hoursPerDay = new SimpleIntegerProperty(8);
		this.efficiency = new SimpleFloatProperty(0.8f);
		this.roles = FXCollections.observableArrayList();
	}

	public String getFirstName() {
		return this.firstName.get();
	}

	public StringProperty firstNameProperty() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public String getLastName() {
		return this.lastName.get();
	}

	public StringProperty lastNameProperty() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public String getId() {
		return this.id.get();
	}

	public StringProperty idProperty() {
		return this.id;
	}

	public void setId(String id) {
		this.id.set(id);
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
		return "Person [firstName=" + firstName.get() + ", lastName=" + lastName.get() + ", id= " + id.get() + "]";
	}

	public void addRole(RoleData role) {
		this.roles.add(role);
	}

	@Override
	public SimulatorType calculateSimulatorType() {
		return null;
	}

	@Override
	public String getIdentifier() {
		return this.getId();
	}

	@Override
	public IdentifiableType getType() {
		return IdentifiableType.PERSON;
	}
}
