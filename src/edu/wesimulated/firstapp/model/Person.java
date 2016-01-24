package edu.wesimulated.firstapp.model;

import com.wesimulated.simulationmotor.operationbased.Entity;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person implements Entity {

	private StringProperty firstName;
	private StringProperty lastName;
	private IntegerProperty hoursPerDay;
	// [Unit] unit of work per hour
	private FloatProperty efficiency;
	private boolean available;
	
	public Person() {
		this(null, null);
	}
	
	public Person(String firstName, String lastName) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.hoursPerDay = new SimpleIntegerProperty(8);
		this.efficiency = new SimpleFloatProperty(0.8f);
		this.available = true;
	}
	
	public float calculateEffectiveMillisecondsPerDay() {
		return this.getHoursPerDay() * this.getEfficiency() /* hour */ * 60 /* minutes/hour */ * 60 /* seconds/minute */ * 1000 /* Milliseconds/seconds */;
	}
	
	@Override
	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public boolean isAvailable() {
		return available;
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
}
