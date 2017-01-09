package edu.wesimulated.firstapp.simulation.stochastic;

/**
 * 
 * @author Carolina
 *
 */
public interface StochasticMethod {

	public StochasticValue evaluate(/** sample */);
	public void train();
	public StochasticMehodType getType();
	public StochasticMethodConfig getConfig();
}
