package edu.wesimulated.firstapp.simulation.stochastic;

import edu.wesimulated.firstapp.model.StochasticVarData;
import edu.wesimulated.firstapp.simulation.stochastic.method.ConstantProbability;

public class StochasticMethodBuilder {

	public static StochasticMethod fromStochasticVarData(StochasticVarData var) {
		StochasticMethodConfig config = StochasticMethodConfig.fromStochasticMethodConfigData(var.getConfig());
		StochasticMehodType type = StochasticMehodType.valueOf(var.getType());
		switch (type) {
		case ConstantProbability:
			return new ConstantProbability(config, var.getName());
		default:
			break;
		}
		return null;
	}

}
