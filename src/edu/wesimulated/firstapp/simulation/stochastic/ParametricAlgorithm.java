package edu.wesimulated.firstapp.simulation.stochastic;

import java.util.Collection;
import java.util.Map;

/**
 * Todas las funciones que se van a hacer para calcular el valor de una variable
 * aleatoria sean de probabilidad o de data mining van a tener ciertos
 * par�metros de entrada. Como la idea es que dichos par�metros sean definidos
 * din�micamente por configuraci�n por c�digo solamente se van a asociar los
 * sacos de estar variables a los algoritmos y por configuraci�n se determinar�
 * cual de ellos se usa.
 * 
 * @author Carolina
 *
 */
public class ParametricAlgorithm {

	private Map<String, EntryValue> availableAttributes;
	public static ParametricAlgorithm buildParamethricAlgorithmForVar(StochasticVar var) {
		return new ParametricAlgorithm(StochasticRegistry.getInstance().getStochasticMethod(var));
	}

	public void consider(NumericallyModeledEntity entity) {
		// TODO implement consider numerically modeled entity
		/**
		 * load values
		 */
	}

	public StochasticValue findSample() {
		// TODO Auto-generated method stub
		/**
		 * get vector of values to pass to the machine learning method Using
		 * machine learnig or other algorithms to calculate a value
		 */
		return this.method.evaluate();
	}

	public void considerAll(Collection<NumericallyModeledEntity> allNumericallyModeledEntities) {
		for (NumericallyModeledEntity numericallyModeledEntity : allNumericallyModeledEntities) {
			availableAttributes.putAll(numericallyModeledEntity.extractValues());
		}
	}
}
