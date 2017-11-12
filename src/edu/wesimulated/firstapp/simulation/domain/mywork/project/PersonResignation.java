package edu.wesimulated.firstapp.simulation.domain.mywork.project;

import java.util.Date;

import edu.wesimulated.firstapp.simulation.domain.Person;

public class PersonResignation extends Risk {

	private Person person;
	private Project project;

	public PersonResignation(float probability, Date periodStart, Date periodEnd, Person person) {
		super(probability, periodStart, periodEnd);
		this.setPerson(person);
	}

	@Override
	public void doAction() {
		if (this.getProject() != null) {
			this.getProject().registerPersonResign(this.person);
			return;
		}
		if (this.getProject() != null) {
			this.getProject().registerPersonResign(this.getProject().pickRandomPerson());
			return;
		}
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	private Project getProject() {
		return this.project;
	}
}
