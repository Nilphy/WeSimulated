package edu.wesimulated.firstapp.simulation.stochastic;

import java.util.HashMap;
import java.util.Map;

import edu.wesimulated.firstapp.model.StochasticRegistryData;
import edu.wesimulated.firstapp.model.StochasticVarData;

/**
 * All configigurations of stochastic variables that are used in the app are
 * registered in this registry
 * 
 * @author Carolina
 *
 */
public class StochasticRegistry {

	private static StochasticRegistry instance;

	private Map<StochasticVar, StochasticMethod> entries;

	private StochasticRegistry() {
	}

	public static StochasticRegistry getInstance() {
		if (instance == null) {
			instance = new StochasticRegistry();
		}
		return instance;
	}

	public StochasticMethod getStochasticMethod(StochasticVar var) {
		return this.entries.get(var);
	}

	public void loadData(StochasticRegistryData stochasticRegistryData) {
		if (stochasticRegistryData != null && stochasticRegistryData.getStochasticVars() != null && stochasticRegistryData.getStochasticVars().size() > 0) {
			this.entries = new HashMap<StochasticVar, StochasticMethod>();
			for (StochasticVarData var : stochasticRegistryData.getStochasticVars()) {
				StochasticVar varType = StochasticVar.valueOf(var.getName());
				StochasticMethod method = StochasticMethodBuilder.fromStochasticVarData(var);
				this.entries.put(varType, method);
			}
		}
	}

}
