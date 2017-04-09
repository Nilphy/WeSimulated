package edu.wesimulated.firstapp.view;

import java.util.HashMap;
import java.util.Map;

import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;

/**
 * This class is created with the intention of having a way to configure the
 * stochastic methods used by the simulation without needing an UI
 * 
 * @author Carolina
 *
 */
public class StochasticVarConfigCodeUI {

	private static StochasticVarConfigCodeUI instance;
	private Map<StochasticVar, Map<String, String>> configOfVar;

	public static StochasticVarConfigCodeUI getInstance() {
		if (instance == null) {
			instance = new StochasticVarConfigCodeUI();
		}
		return instance;
	}

	private StochasticVarConfigCodeUI() {
		this.configOfVar = new HashMap<StochasticVar, Map<String, String>>();
		/**
		 * TODO: Add all stochastic vars config here
		 */
		this.configOfVar.put(StochasticVar.TimeOfWorkSlab, this.buildConfigForTimeOfWorkSlab());
	}

	private Map<String, String> buildConfigForTimeOfWorkSlab() {
		// TODO Auto-generated method stub
		return null;
	}
}
