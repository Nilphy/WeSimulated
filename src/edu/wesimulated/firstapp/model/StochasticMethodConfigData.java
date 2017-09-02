package edu.wesimulated.firstapp.model;

import java.util.Map.Entry;

public class StochasticMethodConfigData {

	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static StochasticMethodConfigData fromEntry(Entry<String, String> config) {
		StochasticMethodConfigData stochasticMethodConfigData = new StochasticMethodConfigData();
		stochasticMethodConfigData.setName(config.getKey());
		stochasticMethodConfigData.setValue(config.getValue());
		return stochasticMethodConfigData;
	}
}
