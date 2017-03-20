package edu.wesimulated.firstapp.simulation.domain;

import java.util.Date;

import com.wesimulated.simulationmotor.des.COperation;

/**
 * Un riesgo va a tener una variable asociada que va a indicar dados ciertos
 * par�metros de entrada si sucede o no en el m�todo test if requirements are
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
	 * Podr�a devolver siempre el siguiente d�a durante el per�odo que tiene
	 * probabilidad de ocurrencia. Otra posibilidad es que si no hay otras
	 * condiciones adem�s de la probabilidad de ocurrencia, se calcule al
	 * principio de la simulaci�n cuando es que va a pasar si va a pasar.
	 */
	@Override
	public Date getDateOfOccurrence() {
		return null;
	}

}
