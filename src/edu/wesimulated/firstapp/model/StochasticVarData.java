package edu.wesimulated.firstapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

public class StochasticVarData {

	private String name;
	private String type;
	private List<StochasticMethodConfigData> config;

	@XmlAttribute
	public String getName() {
		return this.name;
	}

	@XmlAttribute
	public String getType() {
		return this.type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<StochasticMethodConfigData> getConfig() {
		if (config == null) {
			this.config = new ArrayList<>();
		}
		return this.config;
	}

	public void setConfig(List<StochasticMethodConfigData> config) {
		this.config = config;
	}

	public void addConfig(StochasticMethodConfigData config) {
		this.getConfig().add(config);
	}
}
