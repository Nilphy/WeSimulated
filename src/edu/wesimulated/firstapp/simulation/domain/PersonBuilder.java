package edu.wesimulated.firstapp.simulation.domain;

import edu.wesimulated.firstapp.model.PersonData;

public class PersonBuilder {

	public static Person createFromPersonData(PersonData personData, SimulatorFactory factory) {
		Person person = factory.makePerson();
		// TODO populate person with personData
		return person;
	}
}
