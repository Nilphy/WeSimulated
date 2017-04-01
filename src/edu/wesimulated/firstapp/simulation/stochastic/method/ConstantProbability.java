package edu.wesimulated.firstapp.simulation.stochastic.method;

import edu.wesimulated.firstapp.simulation.stochastic.Prediction;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticMehodType;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticMethod;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticMethodConfig;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticMethodConfig.StochasticMethodConfigType;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticValue;

/**
 * This stochastic method that returns always the same value when evaluate is called
 * 
 * @author Carolina
 *
 */
public class ConstantProbability extends StochasticMethod {

	public ConstantProbability(StochasticMethodConfig config, String name) {
		super(config, name);
	}

	public enum StochasticConfig implements StochasticMethodConfigType {
		ConstantValue
	}

	@Override
	public StochasticValue evaluate() {
		Prediction prediction = new Prediction(Double.valueOf(this.getConfig().getValue(StochasticConfig.ConstantValue)));
		StochasticValue value = new StochasticValue(prediction);
		return value;
	}

	@Override
	public void train() {
		throw new RuntimeException("This stochastic method doesnt support training by now");
	}

	@Override
	public StochasticMehodType getType() {
		return StochasticMehodType.ConstantProbability;
	}
}
