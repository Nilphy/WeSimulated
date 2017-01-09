package edu.wesimulated.firstapp.simulation.stochastic;

import java.util.Map;


public class StochasticMethodConfig {

	private Map<String, String> values;
	
	public interface StochasticMethodConfigType {
		
		public String name();
	};
	
	public String getValue(StochasticMethodConfigType key) {
		return values.get(key);
	}
}
