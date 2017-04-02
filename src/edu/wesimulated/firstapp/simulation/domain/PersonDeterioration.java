package edu.wesimulated.firstapp.simulation.domain;

import java.util.Date;

public class PersonDeterioration extends Risk {

	public PersonDeterioration(float probability, Date periodStart, Date periodEnd) {
		super(probability, periodStart, periodEnd);
	}

	private Person person;

	@Override
	public void doAction() {
		double deterioration = Math.random();
		this.person.getProfile().scaleDown(PersonCharacteristic.Efficiency, deterioration);
		this.person.getProfile().scaleDown(PersonCharacteristic.Effectiveness, deterioration);
	}

	private void setPerson(Person person) {
		this.person = person;
	}
}
