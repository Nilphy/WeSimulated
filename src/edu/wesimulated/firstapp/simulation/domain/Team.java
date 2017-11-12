package edu.wesimulated.firstapp.simulation.domain;

import java.util.ArrayList;
import java.util.Collection;

public class Team {

	private String name;
	private Collection<Person> members;

	public boolean hasPersonAsMember(Person person) {
		return this.getMembers().contains(person);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Collection<Person> getMembers() {
		if (this.members == null) {
			this.members = new ArrayList<>();
		}
		return this.members;
	}

	public void addMember(Person person) {
		this.getMembers().add(person);
	}

}
