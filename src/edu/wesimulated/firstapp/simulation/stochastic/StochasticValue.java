package edu.wesimulated.firstapp.simulation.stochastic;

import javax.management.RuntimeErrorException;

/**
 * This class was created with the purpose of unifing algorithms classificators and predictors, 
 * now both are parametric algorithms and retun and stochastic value
 *  
 * @author Carolina
 *
 */
public class StochasticValue {

	private Prediction prediction;
	private Classification classification;

	public StochasticValue(Prediction prediction) {
		this.prediction = prediction;
	}

	public StochasticValue(Classification classification) {
		this.classification = classification;
	}

	public Classification getClassifictation() {
		if (this.classification == null) {
			throw new RuntimeErrorException(null, "Prediction used as Classification");
		}
		return this.classification;
	}

	public Prediction getPrediction() {
		if (this.prediction == null) {
			throw new RuntimeErrorException(null, "Classification used as Prediction");
		}
		return this.prediction;
	}
}
