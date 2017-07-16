package edu.wesimulated.firstapp.simulation.domain;

import java.util.Date;

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
	public Date getDateOfOccurrence() {
		// TODO every 10 minutes
		return null;
	}

	@Override
	public long getPeriodInMinutes() {
		return 10;
	}

}
