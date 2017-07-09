package edu.wesimulated.firstapp.simulation.domain.avature.project;

import java.util.Date;

import edu.wesimulated.firstapp.simulation.domain.Person;
import edu.wesimulated.firstapp.simulation.domain.Project;

public class PersonResignation extends Risk {

	private Person person;
	private Project project;

	public PersonResignation(float probability, Date periodStart, Date periodEnd) {
		super(probability, periodStart, periodEnd);
	}

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
