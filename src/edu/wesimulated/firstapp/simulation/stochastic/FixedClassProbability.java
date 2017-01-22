package edu.wesimulated.firstapp.simulation.stochastic;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/*
 * The configuration values of this stochastic method are the probabilities of each class. The sum has to be equal to one.
 * Internally this class creates an accumulated probability map to have a function that returns the class given a number betwen 0 and 1. 
 * The evaluate method generates a random value between 1 and 0 and returns the class associted with that probability value.
 */
public class FixedClassProbability extends StochasticMethod {

	private Map<Double, String> accumulatedProbabilityFunction;

	public FixedClassProbability(StochasticMethodConfig config, String name) {
		super(config, name);
		this.initializeCalculationData();
	}

	@Override
	public StochasticValue evaluate() {
		double value = Math.random();
		return findClassificationForAccumulatedProbability(value);
	}

	public StochasticValue findClassificationForAccumulatedProbability(double value) {
		Iterator<Double> accumulatedProbabilityIterator = this.accumulatedProbabilityFunction.keySet().iterator();
		Double accumulatedProbabilityValue = null;
		while (accumulatedProbabilityIterator.hasNext()) {
			accumulatedProbabilityValue = accumulatedProbabilityIterator.next();
			if (accumulatedProbabilityValue > value) {
				break;
			}
		}
		return new StochasticValue(ClassifictionBuilder.buildFromName(this.accumulatedProbabilityFunction.get(accumulatedProbabilityValue)));
	}

	@Override
	public void train() {
		throw new RuntimeException("This stochastic method doesnt support training by now");
	}

	@Override
	public StochasticMehodType getType() {
		return StochasticMehodType.FixedClassProbability;
	}

	private void initializeCalculationData() {
		this.accumulatedProbabilityFunction = new TreeMap<Double, String>((Double d1, Double d2) -> Double.compare(d1, d2));
		double accumulatedProbability = 0;
		for (Entry<String, String> entry : this.getConfig().getEntries()) {
			String classification = entry.getKey();
			accumulatedProbability += Double.parseDouble(entry.getValue());
			this.accumulatedProbabilityFunction.put(accumulatedProbability, classification);
		}
	}
}
