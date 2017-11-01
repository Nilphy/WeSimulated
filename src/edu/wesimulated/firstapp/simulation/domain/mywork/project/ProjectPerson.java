package edu.wesimulated.firstapp.simulation.domain.mywork.project;

import edu.wesimulated.firstapp.simulation.domain.Person;

public class ProjectPerson extends Person {

	public void resign() {
		this.getProject().getPeople().remove(this);
	}
}
