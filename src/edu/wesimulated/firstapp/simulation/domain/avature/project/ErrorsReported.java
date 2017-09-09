package edu.wesimulated.firstapp.simulation.domain.avature.project;


public class ErrorsReported extends MaintenanceTask {

	public ErrorsReported(long period) {
		super(period);
	}

	@Override
	public boolean testIfRequirementsAreMet() {
		// FIXME is it time? that should be considered in parent class?
		return true;
	}

	@Override
	public void doAction() {
		super.doAction();
		// TODO generate squealer activity
		
	}
}
