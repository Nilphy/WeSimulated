package edu.wesimulated.firstapp.simulation.domain.avature.project;

import java.util.Collection;
import java.util.Date;

import com.wesimulated.simulationmotor.DateUtils;
import com.wesimulated.simulationmotor.des.BOperation;
import com.wesimulated.simulationmotor.des.COperation;

/**
 * The difference between a maintenance task and a risk is that maintenance
 * tasks use to happen more than once. When an ocurrence ends it calculates if
 * another one is added to the project for the future
 * 
 * @author Carolina
 *
 */
public abstract class MaintenanceTask extends COperation {

	private Date lastTime;
	private long period;

	public MaintenanceTask(long period) {
		this.period = period;
	}

	public long getPeriodInMinutes() {
		return period;
	}

	@Override
	public boolean testIfRequirementsAreMet() {
		return true;
	}

	@Override
	public Date getDateOfOccurrence(Date actualDate) {
		Date tenMinutesFromNow = DateUtils.addMilis(lastTime, this.getPeriodInMinutes() * DateUtils.MILLIES_IN_MINUTE);
		if (tenMinutesFromNow.before(DateUtils.findEndOfLaboralDay(lastTime))) {
			return DateUtils.findStartOfNextLaboralDay(lastTime);
		} else {
			return tenMinutesFromNow;
		}
	}
}
