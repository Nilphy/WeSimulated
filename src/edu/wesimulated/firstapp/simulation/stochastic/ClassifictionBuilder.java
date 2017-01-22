package edu.wesimulated.firstapp.simulation.stochastic;

public class ClassifictionBuilder {

	public static Classification buildFromName(String name) {
		return new DummyClassification(name);
	}

}
