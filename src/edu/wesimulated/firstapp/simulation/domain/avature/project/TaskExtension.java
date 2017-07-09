package edu.wesimulated.firstapp.simulation.domain.avature.project;

import java.util.Date;

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
