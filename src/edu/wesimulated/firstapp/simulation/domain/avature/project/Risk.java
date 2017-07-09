package edu.wesimulated.firstapp.simulation.domain.avature.project;

import java.util.Date;
import java.util.Random;

import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.COperation;

import edu.wesimulated.firstapp.view.ThingsWithoutAUi;

/**
 * Un riesgo es un evento que tiene cierta probabilidad de ocurrencia en el
 * proyecto, cuyos efectos no son siempre negativos.
 * 
 * Posibles riesgos podrían ser la rotura de equipo de trabajo, renuncias de
 * peronal, problemas peronales, enfermedades, etc
 * 
 * @author Carolina
 *
 */
public abstract class Risk extends COperation {

	private float probability;
	private Random rand;
	private Date dateOfOccurrence;

	public Risk(float probability, Date periodStart, Date periodEnd) {
		this.probability = probability;
		this.rand = new Random(ThingsWithoutAUi.RANDOM_SEED);
		if (this.evaluateConsideringProbability()) {
			float proportion = this.rand.nextFloat();
			this.dateOfOccurrence = DateUtils.calculateProportionalDateInPeriod(periodStart, periodEnd, proportion);
		}
	}

	@Override
	public boolean testIfRequirementsAreMet() {
		return this.getDateOfOccurrence() != null;
	}

	private boolean evaluateConsideringProbability() {
		return rand.nextFloat() < probability;
	}

	@Override
	public Date getDateOfOccurrence() {
		return dateOfOccurrence;
	}

}
