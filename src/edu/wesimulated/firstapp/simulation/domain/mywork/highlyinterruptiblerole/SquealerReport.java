package edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole;

import java.util.Date;

import com.wesimulated.simulation.Clock;

import edu.wesimulated.firstapp.simulation.stochastic.ParametricAlgorithm;
import edu.wesimulated.firstapp.simulation.stochastic.StochasticVar;

public class SquealerReport extends Message {

	protected static Status[] statusThatRequireWork = { Status.ISSUED, Status.NOT_ISSUED, Status.RESOLVE };

	public SquealerReport(HighlyInterruptibleRolePerson sender, Date timestamp, Clock clock) {
		super(sender, null, timestamp, clock);
	}

	/**
	 * Decide if after new a message is in state ISSUED or NOT_ISSUED
	 */
	public void analize() {
		ParametricAlgorithm status = ParametricAlgorithm.buildParametricAlgorithmForVar(StochasticVar.SquealerMessageStatus);
		status.consider(this);
		this.setStatus((Status) status.findSample().getClassifictation());
	}
	
	public boolean isHappeningMoreThanUsual() {
		/**
		 * FIXME Guardar todos los que pasaron hoy y tener una colección de cuanto pasan en promedio por día y agregarle el field prioridad
		 */
		return false;
	}
}
