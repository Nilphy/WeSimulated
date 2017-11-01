package edu.wesimulated.firstapp.simulation.domain.mywork.project;

public class ErrorsReported extends MaintenanceTask {

	public ErrorsReported(long period) {
		super(period);
	}

	/**
	 * This action generates errors in already completed tasks, then this errors
	 * must be assigned to some person (which since this moment could be modeled
	 * as a highly interruptible role)
	 */
	@Override
	public void doAction() {
		// TODO generate squealer activity
	}
}
