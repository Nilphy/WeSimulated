package edu.wesimulated.firstapp.simulation.stochastic;

public class DummyClassification implements Classification {

	private String name;

	public DummyClassification(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
