package edu.wesimulated.firstapp.simulation.domain;

import java.util.Date;

public class PersonResignation extends Risk {

	public PersonResignation(float probability, Date periodStart, Date periodEnd) {
		super(probability, periodStart, periodEnd);
	}

	private Person person;
	private Project project;

	@Override
	public void doAction() {
		if (this.person != null) {
			this.person.resign();
		}
		if (this.project != null) {
			this.project.pickRandomPerson().resign();
		}
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
