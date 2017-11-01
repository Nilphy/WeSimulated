package edu.wesimulated.firstapp.simulation.domain.mywork.project;

import java.util.Date;

import edu.wesimulated.firstapp.simulation.domain.Project;

public class PersonResignation extends Risk {

	private ProjectPerson person;
	private Project project;

	public PersonResignation(float probability, Date periodStart, Date periodEnd, ProjectPerson person) {
		super(probability, periodStart, periodEnd);
		this.setPerson(person);
	}

	@Override
	public void doAction() {
		if (this.person != null) {
			this.person.resign();
			return;
		}
		if (this.project != null) {
			((ProjectPerson) this.project.pickRandomPerson()).resign();
			return;
		}
	}

	public void setPerson(ProjectPerson person) {
		this.person = person;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
