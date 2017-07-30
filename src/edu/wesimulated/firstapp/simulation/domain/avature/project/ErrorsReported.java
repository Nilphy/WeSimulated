package edu.wesimulated.firstapp.simulation.domain.avature.project;

import java.util.Date;

import com.wesimulated.simulationmotor.DateUtils;

public class ErrorsReported extends MaintenanceTask {

	@Override
	public boolean testIfRequirementsAreMet() {
		// TODO decide the requirements
		return false;
	}

	@Override
	public void doAction() {
		// TODO generate squealer activity
		
	}

	@Override
	public long getPeriodInMinutes() {
		return 10;
	}

	@Override
	public Date getDateOfOccurrence(Date actualDate) {
		Date tenMinutesFromNow = DateUtils.addMilis(actualDate, this.getPeriodInMinutes() * DateUtils.MILLIES_IN_MINUTE);
		if (tenMinutesFromNow.before(DateUtils.findEndOfLaboralDay(actualDate))) {
			return DateUtils.findStartOfNextLaboralDay(actualDate);
		} else {
			return tenMinutesFromNow;
		}
	}

}
