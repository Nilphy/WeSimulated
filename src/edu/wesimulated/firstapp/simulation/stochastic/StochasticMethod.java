package edu.wesimulated.firstapp.simulation.stochastic;

/**
 * 
 * @author Carolina
 *
 */
public abstract class StochasticMethod {

	private StochasticMethodConfig config;
	private String name;

	public StochasticMethod(StochasticMethodConfig config, String name) {
		this.config = config;
		this.name = name;
	}

	/**
	 * I'm probably missing the version with a aparameter on it
	 * @return
	 */
	public abstract StochasticValue evaluate();

	public abstract void train();

	public abstract StochasticMehodType getType();

	public StochasticMethodConfig getConfig() {
		return this.config;
	}
}
