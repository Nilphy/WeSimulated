package edu.wesimulated.firstapp.simulation.domain.avature.project;

import java.util.Date;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.PersonCharacteristic;

public class PersonDeterioration extends Risk {

	public PersonDeterioration(float probability, Date periodStart, Date periodEnd, Person person) {
		super(probability, periodStart, periodEnd);
		this.setPerson(person);
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
