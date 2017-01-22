package edu.wesimulated.firstapp.simulation.stochastic;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.wesimulated.firstapp.model.StochasticMethodConfigData;


public class StochasticMethodConfig {

	private Map<String, String> values;
	
	public StochasticMethodConfig(Map<String, String> values) {
		this.values = values;
	}
	
	public interface StochasticMethodConfigType {
		public String name();
	};
	
	public String getValue(StochasticMethodConfigType key) {
		return values.get(key);
	}
	
	public String getValue(String key) {
		return values.get(key);
	}

	public Collection<String> getKeys() {
		return values.keySet();
	}
	
	public Set<Entry<String, String>> getEntries() {
		return values.entrySet();
	}

	public static StochasticMethodConfig fromStochasticMethodConfigData(List<StochasticMethodConfigData> config) {
		Map<String, String> configValues = new HashMap<>();
		for (StochasticMethodConfigData stochasticMethodConfigData : config) {
			configValues.put(stochasticMethodConfigData.getName(), stochasticMethodConfigData.getValue());
		}
		return new StochasticMethodConfig(configValues);
	}
}
