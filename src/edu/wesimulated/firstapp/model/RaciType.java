package edu.wesimulated.firstapp.model;

public enum RaciType {

	Responsible("R"), Accountable("A"), Consulted("C"), Informed("I");

	private String initial;

	RaciType(String initial) {
		this.initial = initial;
	}

	public String toString() {
		return initial;
	}
}
