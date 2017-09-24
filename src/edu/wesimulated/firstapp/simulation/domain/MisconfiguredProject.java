package edu.wesimulated.firstapp.simulation.domain;

public class MisconfiguredProject extends Exception {

	private static final long serialVersionUID = 1L;

	public MisconfiguredProject(String reason) {
		super(reason);
	}
}
