package edu.wesimulated.firstapp.simulation.domain.avatureproject;

import java.util.Date;

import edu.wesimulated.firstapp.simulation.domain.Risk;

public class TaskExtension extends Risk {

	private AvatureProjectTask task;

	public TaskExtension(float probability, Date periodStart, Date periodEnd) {
		super(probability, periodStart, periodEnd);
	}

	@Override
	public void doAction() {
		double scale = Math.random();
		task.extendDuration(scale);
	}

	public void setTask(AvatureProjectTask task) {
		this.task = task;
	}
}
