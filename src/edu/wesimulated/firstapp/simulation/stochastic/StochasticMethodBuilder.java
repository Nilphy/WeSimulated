package edu.wesimulated.firstapp.simulation.stochastic;

import edu.wesimulated.firstapp.model.StochasticVarData;
import edu.wesimulated.firstapp.simulation.stochastic.method.ConstantProbability;

public class StochasticMethodBuilder {

	public static StochasticMethod fromStochasticVarData(StochasticVarData var) {
		StochasticMehodType type = StochasticMehodType.valueOf(var.getType());
		StochasticMethodConfig config = new StochasticMethodConfig();
		switch (type) {
		case ConstantProbability:
			return new ConstantProbability(config);
		default:
			break;
		}
		return null;
	}

}
