package edu.wesimulated.firstapp.simulation.domain;


public interface Identifiable {

	public enum IdentifiableType {
		PERSON, TASK, ROLE, PROJECT
	}

	public String getIdentifier();

	public IdentifiableType getType();
}
