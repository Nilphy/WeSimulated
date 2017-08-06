package edu.wesimulated.firstapp.simulation.stochastic;

public class ClassificationBuilder {

	public static Classification buildFromName(String name) {
		return new DummyClassification(name);
	}

}
