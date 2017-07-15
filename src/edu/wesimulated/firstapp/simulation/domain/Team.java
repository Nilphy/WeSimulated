package edu.wesimulated.firstapp.simulation.domain;

import java.util.Collection;

public class Team {

	private Collection<Person> members;
	
	public boolean hasPersonAsMember(Person person) {
		return this.members.contains(person);
	}

}
