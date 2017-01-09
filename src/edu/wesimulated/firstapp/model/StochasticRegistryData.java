package edu.wesimulated.firstapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "stochasticRegistryData")
public class StochasticRegistryData {

	private List<StochasticVarData> stochasticVars;

	@XmlElement(name = "stochasticVar")
	public List<StochasticVarData> getStochasticVars() {
		if (stochasticVars == null) {
			this.stochasticVars = new ArrayList<>();
		}
		return this.stochasticVars;
	}

	public void setStochasticVars(List<StochasticVarData> stochasticVars) {
		this.stochasticVars = stochasticVars;
	}
}
