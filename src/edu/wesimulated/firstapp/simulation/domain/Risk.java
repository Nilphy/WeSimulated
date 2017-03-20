package edu.wesimulated.firstapp.simulation.domain;

import java.util.Date;

import com.wesimulated.simulationmotor.des.COperation;

/**
 * Un riesgo va a tener una variable asociada que va a indicar dados ciertos
 * parámetros de entrada si sucede o no en el método test if requirements are
 * met.
 * 
 * @author Carolina
 *
 */
public abstract class Risk extends COperation {

	@Override
	public boolean testIfRequirementsAreMet() {
		/**
		 * TODO probabilidad y otras cosas que deban estar activas para que se manifieste este riesgo
		 */
		return false;
	}

	/**
	 * Podría devolver siempre el siguiente día durante el período que tiene
	 * probabilidad de ocurrencia. Otra posibilidad es que si no hay otras
	 * condiciones además de la probabilidad de ocurrencia, se calcule al
	 * principio de la simulación cuando es que va a pasar si va a pasar.
	 */
	@Override
	public Date getDateOfOccurrence() {
		return null;
	}

}
